package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto extends RepresentationModel<UserDto> {
    private int id;
    private String name;
    @JsonIgnore
    private List<OrderDto> orderList;
}
