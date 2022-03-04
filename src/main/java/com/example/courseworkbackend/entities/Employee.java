package com.example.courseworkbackend.entities;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Employees")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_employee;

    @ManyToOne(fetch = FetchType.LAZY)
    private Human human;

    @ManyToOne(fetch = FetchType.LAZY)
    private Position position;

    private Integer experience;
    private Integer accessLevel;
    private Timestamp startTime;
    private Timestamp endTime;
}
