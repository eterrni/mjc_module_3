package com.epam.esm.util;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class HATEOASBuilder {

    public static List<TagDto> addLinksToTagDto(List<TagDto> tagDtoList) {
        for (TagDto tagDto : tagDtoList) {
            tagDto.add(linkTo(methodOn(TagController.class).read(tagDto.getId())).withSelfRel());
        }
        return tagDtoList;
    }

    public static TagDto addLinksToTagDto(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(TagController.class).delete(tagDto.getId())).withRel("delete"));
        return tagDto;
    }

    public static List<GiftCertificateDto> addLinksToCertificateDto(List<GiftCertificateDto> certificateDtoList) {
        for (GiftCertificateDto giftCertificateDto : certificateDtoList) {
            giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).read(giftCertificateDto.getId())).withSelfRel());
            addLinksToTagDto(giftCertificateDto.getTags());
        }
        return certificateDtoList;
    }

    public static GiftCertificateDto addLinksToCertificateDto(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).update(giftCertificateDto.getId(), giftCertificateDto)).withRel("update"));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).delete(giftCertificateDto.getId())).withRel("delete"));
        addLinksToTagDto(giftCertificateDto.getTags());
        return giftCertificateDto;
    }

    public static PagedModel<TagDto> addPaginationToTags(List<TagDto> tags, int page, int size, long countEntities) {

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, countEntities);
        long maxPage = pageMetadata.getTotalPages();
        List<Link> linkList = new ArrayList<>();
        if (page > 1) {
            Link previous = WebMvcLinkBuilder.linkTo(methodOn(TagController.class).readAll(page - 1, size)).withRel("previous");
            linkList.add(previous);
        }
        if (maxPage > page) {
            Link next = WebMvcLinkBuilder.linkTo(methodOn(TagController.class).readAll(page + 1, size)).withRel("next");
            linkList.add(next);
        }
        return PagedModel.of(tags, pageMetadata, linkList);

    }

    public static PagedModel<GiftCertificateDto> addPaginationToCertificates(List<GiftCertificateDto> certificateDtoList, GiftCertificateQueryParameter parameter, int page, int size, long countEntities) {

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, countEntities);
        long maxPage = pageMetadata.getTotalPages();
        List<Link> linkList = new ArrayList<>();
        if (page > 1) {
            Link previous = WebMvcLinkBuilder.linkTo(methodOn(GiftCertificateController.class).readAll(page - 1, size, parameter)).withRel("previous");
            linkList.add(previous);
        }
        if (maxPage > page) {
            Link next = WebMvcLinkBuilder.linkTo(methodOn(GiftCertificateController.class).readAll(page + 1, size, parameter)).withRel("next");
            linkList.add(next);
        }
        return PagedModel.of(certificateDtoList, pageMetadata, linkList);

    }

}
