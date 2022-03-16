package com.example.courseworkbackend.entities.dao.requests;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MonsterD {

    private Long id_rift;
    private Long id_type;
    private Integer rank;

}
