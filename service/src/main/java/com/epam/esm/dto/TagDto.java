package com.epam.esm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@NoArgsConstructor
@Relation(itemRelation = "tag", collectionRelation = "tags")
public class TagDto extends RepresentationModel<TagDto> {
    private int id;
    private String name;
}
