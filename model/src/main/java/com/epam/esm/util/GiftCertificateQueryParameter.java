package com.epam.esm.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@Data
public class GiftCertificateQueryParameter {
    private String name;
    private String description;
    private String tagName;
    private SortType sortType;
    private OrderType orderType;

    public GiftCertificateQueryParameter(String name, String description, String tagName, String sortType, String orderType) {
        if (!StringUtils.isEmpty(name)) {
            setName(name);
        }
        if (!StringUtils.isEmpty(description)) {
            setDescription(description);
        }
        if (!StringUtils.isEmpty(tagName)) {
            setTagName(tagName);
        }
        if (!StringUtils.isEmpty(sortType)) {
            setSortType(sortType.toUpperCase());
        }
        if (!StringUtils.isEmpty(orderType)) {
            setOrderType(orderType.toUpperCase());
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
        return name == null && description == null && tagName == null && sortType == null && orderType == null;
    }

}