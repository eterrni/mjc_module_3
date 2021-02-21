package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class GiftCertificateCriteriaBuilder {
    private static final String SYMBOL = "%";
    private static final String PARAMETER_TAG_NAME_LIST = "tagNames";
    private static final String SELECT_TAG_BY_NAME = "from Tag tag where tag.name in (:" + PARAMETER_TAG_NAME_LIST + ")";

    private GiftCertificateCriteriaBuilder() {
    }

    public static CriteriaQuery<GiftCertificate> build(EntityManager entityManager, GiftCertificateQueryParameter giftCertificateQueryParameter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);

        List<Predicate> predicateList = new ArrayList<>();

        String name = giftCertificateQueryParameter.getName();
        if (Objects.nonNull(name)) {
            Predicate predicate = criteriaBuilder.like(giftCertificateRoot.get("name"),
                    SYMBOL + name + SYMBOL);
            predicateList.add(predicate);
        }

        String description = giftCertificateQueryParameter.getDescription();
        if (Objects.nonNull(description)) {
            Predicate predicate = criteriaBuilder.like(giftCertificateRoot.get("description"),
                    SYMBOL + description + SYMBOL);
            predicateList.add(predicate);
        }

        List<String> tagNameList = giftCertificateQueryParameter.getTagNamesList();
        if (tagNameList != null) {
            List<String> tagNamesWithoutDuplicates = tagNameList.stream().distinct().collect(Collectors.toList());

            List<Tag> tags = entityManager.createQuery(SELECT_TAG_BY_NAME, Tag.class)
                    .setParameter(PARAMETER_TAG_NAME_LIST, tagNamesWithoutDuplicates).getResultList();

            if (tags.size() != tagNamesWithoutDuplicates.size()) {
                return criteriaQuery;
            }

            tags.forEach(tag -> predicateList.add(criteriaBuilder.isMember(tag, giftCertificateRoot.get("tags"))));
        }

        criteriaQuery.select(giftCertificateRoot).where(predicateList.toArray(new Predicate[0]));

        SortType sortType = giftCertificateQueryParameter.getSortType();
        OrderType orderType = giftCertificateQueryParameter.getOrderType();

        String columnNameToSort = null;

        if (Objects.nonNull(sortType)) {
            switch (sortType) {
                case NAME:
                    columnNameToSort = "name";
                    break;
                case CREATE_DATE:
                    columnNameToSort = "createDate";
                    break;
            }
        }

        if (Objects.nonNull(orderType)) {
            Order order = null;
            if (Objects.isNull(columnNameToSort)) {
                columnNameToSort = "name";
            }

            switch (orderType) {
                case ASC:
                    order = criteriaBuilder.asc(giftCertificateRoot.get(columnNameToSort));
                    break;

                case DESC:
                    order = criteriaBuilder.desc(giftCertificateRoot.get(columnNameToSort));
                    break;
            }

            criteriaQuery.orderBy(order);
        }

        return criteriaQuery;
    }
}
