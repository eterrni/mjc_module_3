package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * An interface that inherits the CRD interface and complements it with the methods required for the tag
 */
public interface ITagRepository extends ICRDRepository<Tag, Integer> {
    /**
     * Get entity
     *
     * @param tagName of the entity we want to get from database
     * @return the entity with this tag name
     */
    Optional<Tag> readTagByName(String tagName);
}
