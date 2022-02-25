package com.example.courseworkbackend.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Rifts_status")
@NoArgsConstructor
@AllArgsConstructor
public class RiftStatus {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rift id_rift;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group_id;

    private boolean Rift_condition;
    private Timestamp time;
    private Timestamp time_to_open;
    private boolean result;

}
