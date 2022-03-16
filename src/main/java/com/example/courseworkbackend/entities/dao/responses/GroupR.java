package com.example.courseworkbackend.entities.dao.responses;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GroupR {

    private Long id;
    private String name;
    private Integer accessLevel;

}
