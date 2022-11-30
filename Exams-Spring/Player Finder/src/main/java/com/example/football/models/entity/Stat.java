package com.example.football.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Table(name = "stats")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stat extends BaseEntity{

    @Column(nullable = false)
    @Positive
    private Double shooting;

    @Column(nullable = false)
    @Positive
    private Double passing;

    @Column(nullable = false)
    @Positive
    private Double endurance;
}
