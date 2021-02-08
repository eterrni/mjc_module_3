package com.epam.esm.controller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class HATEOASController<T> {
    public abstract PagedModel<T> readAll(int page, int size);

    protected PagedModel<T> addPagination(Class<? extends HATEOASController<T>> clazz, List<T> entities, int page, int size, long countOfEntities) {
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, countOfEntities);
        long maxPage = pageMetadata.getTotalPages();
        List<Link> linkList = new ArrayList<>();
        if (page > 1) {
            Link previous = WebMvcLinkBuilder.linkTo(methodOn(clazz).readAll(page - 1, size)).withRel("previous");
            linkList.add(previous);
        }
        if (maxPage > page) {
            Link next = WebMvcLinkBuilder.linkTo(methodOn(clazz).readAll(page + 1, size)).withRel("next");
            linkList.add(next);
        }
        return PagedModel.of(entities, pageMetadata, linkList);

    }

}
