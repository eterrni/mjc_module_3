package com.epam.esm.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateParameterOrder {
    private int userID;
    private List<Integer> giftsId;
}
