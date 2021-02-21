package com.epam.esm.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@NoArgsConstructor
@Data
public final class GiftCertificateQueryParameter {
    private String name;
    private String description;
    private List<String> tagNamesList;
    private SortType sortType;
    private OrderType orderType;

    public GiftCertificateQueryParameter(String name, String description, List<String> tagNamesList, String sortType, String orderType) {
        if (!StringUtils.isEmpty(name)) {
            setName(name);
        }
        if (!StringUtils.isEmpty(description)) {
            setDescription(description);
        }
        if (!StringUtils.isEmpty(sortType)) {
            setSortType(sortType.toUpperCase());
        }
        if (!StringUtils.isEmpty(orderType)) {
            setOrderType(orderType.toUpperCase());
        }
        if (tagNamesList != null && !tagNamesList.isEmpty()) {
            this.tagNamesList = tagNamesList;
        }
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = SortType.valueOf(sortType.toUpperCase());
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = OrderType.valueOf(orderType.toUpperCase());
    }

    public boolean isEmpty() {
        return name == null && description == null && tagNamesList == null && sortType == null && orderType == null;
    }

}
