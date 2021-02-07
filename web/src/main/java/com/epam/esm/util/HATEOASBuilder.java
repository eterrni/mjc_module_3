package com.epam.esm.util;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class HATEOASBuilder {

    public static List<TagDto> addLinksToTagDto(List<TagDto> tagDtos) {
        for (TagDto tagDto : tagDtos) {
            tagDto.add(linkTo(methodOn(TagController.class).read(tagDto.getId())).withSelfRel());
        }
        return tagDtos;
    }

    public static TagDto addLinksToTagDto(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(TagController.class).delete(tagDto.getId())).withRel("delete"));
        return tagDto;
    }

    public static List<GiftCertificateDto> addLinksToCertificateDto(List<GiftCertificateDto> giftCertificateDtos) {
        for (GiftCertificateDto giftCertificateDto : giftCertificateDtos) {
            giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).read(giftCertificateDto.getId())).withSelfRel());
            addLinksToTagDto(giftCertificateDto.getTags());
        }
        return giftCertificateDtos;
    }

    public static GiftCertificateDto addLinksToCertificateDto(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).update(giftCertificateDto.getId(), giftCertificateDto)).withRel("update"));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).delete(giftCertificateDto.getId())).withRel("delete"));
        addLinksToTagDto(giftCertificateDto.getTags());
        return giftCertificateDto;
    }

}
