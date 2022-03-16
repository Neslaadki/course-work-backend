package com.example.courseworkbackend.entities;

import lombok.*;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Awakeners")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Awakener {

    @Id
    @Column(name = "id_human")
    private Long id_awakener;

    @OneToOne(orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "id_human")
    private Human human;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Guild guild;
    @NotNull
    @Min(0)
    @Max(7)
    private Integer rank;
    @NotNull
    @Min(0)
    private Integer experience;
    @NotNull
    private Timestamp awakeTime;


}
