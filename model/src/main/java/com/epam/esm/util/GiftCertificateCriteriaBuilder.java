package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GiftCertificateCriteriaBuilder {

    private static final GiftCertificateCriteriaBuilder instance = new GiftCertificateCriteriaBuilder();
    private static final String ANY_SYMBOL = "%";

    private GiftCertificateCriteriaBuilder() {
    }

    public static GiftCertificateCriteriaBuilder getInstance() {
        return instance;
    }

    public CriteriaQuery<GiftCertificate> build(CriteriaBuilder criteriaBuilder, GiftCertificateQueryParameter giftCertificateQueryParameter) {

        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);

        List<Predicate> predicateList = new ArrayList<>();

        String name = giftCertificateQueryParameter.getName();
        if (Objects.nonNull(name)) {
            Predicate predicate = criteriaBuilder.like(giftCertificateRoot.get("name"),
                    ANY_SYMBOL + name + ANY_SYMBOL);
            predicateList.add(predicate);
        }

        String description = giftCertificateQueryParameter.getDescription();
        if (Objects.nonNull(description)) {
            Predicate predicate = criteriaBuilder.like(giftCertificateRoot.get("description"),
                    ANY_SYMBOL + description + ANY_SYMBOL);
            predicateList.add(predicate);
        }

        String tagName = giftCertificateQueryParameter.getTagName();
        if (Objects.nonNull(tagName)) {
            Join<GiftCertificate, Tag> tagJoin = giftCertificateRoot.join("tags");
            Predicate predicate = criteriaBuilder.equal(tagJoin.get("name"), tagName);
            predicateList.add(predicate);
        }

        criteriaQuery.select(giftCertificateRoot).where(predicateList.toArray(new Predicate[0]));

        SortType sortType = giftCertificateQueryParameter.getSortType();
        OrderType orderType = giftCertificateQueryParameter.getOrderType();

        String columnNameToSort = null;

        if (Objects.nonNull(sortType)) {
            if (Objects.isNull(orderType)) {
                orderType = OrderType.ASC;
            }

            if (sortType == SortType.NAME) {
                columnNameToSort = "name";
            } else if (sortType == SortType.CREATE_DATE) {
                columnNameToSort = "createDate";
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
