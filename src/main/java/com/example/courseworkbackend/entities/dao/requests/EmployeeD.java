package com.example.courseworkbackend.entities.dao.requests;

import com.example.courseworkbackend.entities.Country;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class EmployeeD {
    private String login;
    private String password;
    private Long id_human; // если null, то новый Human

    private String firstName;
    private String lastName;
    private Timestamp birthday;
    private Long countryId;

    private Integer experience;
    private Integer accessLevel;
    private Long positionId;

}
