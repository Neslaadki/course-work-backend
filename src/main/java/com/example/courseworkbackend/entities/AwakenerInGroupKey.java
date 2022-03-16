package com.example.courseworkbackend.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Accessors(chain = true)
@Getter
@Setter
public class AwakenerInGroupKey implements Serializable {


    private static final long serialVersionUID = -7821055594688334072L;
    @ManyToOne
    @JoinColumn(name = "id_human")
    private Human human;


    @ManyToOne
    @JoinColumn(name = "id_group")
    private Group group;
}
