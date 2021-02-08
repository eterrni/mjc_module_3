package com.epam.esm.util;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class HATEOASBuilder {

    public static GiftCertificateDto addLinksToCertificateDto(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).update(giftCertificateDto.getId(), giftCertificateDto)).withRel("update"));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).delete(giftCertificateDto.getId())).withRel("delete"));
        addLinksToListTagDto(giftCertificateDto.getTags());
        return giftCertificateDto;
    }

    public static TagDto addLinksToTagDto(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(TagController.class).delete(tagDto.getId())).withRel("delete"));
        return tagDto;
    }

    public static UserDto addLinksToUser(UserDto user) {
        user.add(linkTo(methodOn(UserController.class).read(user.getId())).withSelfRel());
        return user;
    }

    public static OrderDto addLinksToOrder(OrderDto orderDto) {
        addLinksToListCertificateDto(orderDto.getGiftCertificateList());
        addLinksToUser(orderDto.getUser());
        return orderDto;
    }

    public static void addLinksToListCertificateDto(List<GiftCertificateDto> certificateDtoList) {
        for (GiftCertificateDto giftCertificateDto : certificateDtoList) {
            giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).read(giftCertificateDto.getId())).withSelfRel());
            addLinksToListTagDto(giftCertificateDto.getTags());
        }
    }

    public static void addLinksToListTagDto(List<TagDto> tagDtoList) {
        for (TagDto tagDto : tagDtoList) {
            tagDto.add(linkTo(methodOn(TagController.class).read(tagDto.getId())).withSelfRel());
        }
    }

    public static void addLinksToListUser(List<UserDto> userDtoList) {
        for (UserDto userDto : userDtoList) {
            userDto.add(linkTo(methodOn(OrderController.class).readByUserId(userDto.getId())).withRel("all user orders"));
        }
    }

    public static void addLinksToListOrder(List<OrderDto> orderDtoList) {
        for (OrderDto orderDto : orderDtoList) {
            orderDto.add(linkTo(methodOn(OrderController.class).readByUserId(orderDto.getUser().getId())).withRel("user orders"));
            addLinksToListCertificateDto(orderDto.getGiftCertificateList());
            addLinksToUser(orderDto.getUser());
        }
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
