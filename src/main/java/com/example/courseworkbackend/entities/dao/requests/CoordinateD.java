package com.example.courseworkbackend.entities.dao.requests;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CoordinateD {

    private Float latitude;
    private Float longitude;

}
