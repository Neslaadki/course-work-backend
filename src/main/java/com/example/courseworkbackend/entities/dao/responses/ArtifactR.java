package com.example.courseworkbackend.entities.dao.responses;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ArtifactR {

    private Long artifactId;
    private int price;
    private String typeName;

}
