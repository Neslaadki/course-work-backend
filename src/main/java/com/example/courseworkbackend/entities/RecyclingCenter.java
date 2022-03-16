package com.example.courseworkbackend.entities;


import lombok.*;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "Recycling_centers")
@NoArgsConstructor
@AllArgsConstructor
public class RecyclingCenter {

    @Id
    @Column(name = "id_coordinate")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_coordinate")
    private Coordinate coordinate;

    @ManyToOne
    @NotNull
    private Types type;

    /*
    Было принято решение добавить поле с сылкой на страну, в которой находится центр
    Так как для проверки нахождения разлома в стране необходимо было делать слияние трех таблиц в одну + проверку по id,
        что дает неприемлимую вычислительную нагрузку.
     */

    @ManyToOne
    @JoinColumn(name = "id_country")
    private Country country;

    private Integer access_level;

}
