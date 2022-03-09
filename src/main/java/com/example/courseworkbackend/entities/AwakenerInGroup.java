package com.example.courseworkbackend.entities;


import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@ToString
@Table(name = "AwakenersInGroup")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AwakenerInGroup {

    @EmbeddedId
    private AwakenerInGroupKey awakenerInGroupKey;
    private Timestamp joinTime;
    private Timestamp endTime;

}
