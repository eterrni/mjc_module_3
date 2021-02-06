package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private int id;
    private String name;
//    @JsonIgnoreProperties(value = {"user", "giftCertificateList"})
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnore
    private List<OrderDto> orderList;
}
