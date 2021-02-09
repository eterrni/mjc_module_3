package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

/**
 * An interface that inherits the CRD interface and complements it with the methods required for the tag
 *
 * @author Alexander Novikov
 */
public interface ITagService extends ICRDService<TagDto, Integer> {
    /**
     * Get count of exist tags.
     *
     * @return number of exist tags.
     */
    long getCountOfEntities();

    /**
     * Get the most widely used tag from user with highest cost of all orders.
     *
     * @return {@link TagDto}
     */
    TagDto getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders();
}
