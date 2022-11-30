package com.example.football.models.entity;

import com.example.football.constants.PositionPlayerEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Table(name = "players")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player extends BaseEntity{

    @Column(nullable = false, name = "first_name")
    @Size(min = 3)
    private String firstName;

    @Column(nullable = false, name = "last_name")
    @Size(min = 3)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PositionPlayerEnum position;

    @ManyToOne
    private Town towns;
    @ManyToOne
    private Team teams;
    @ManyToOne
    private Stat stats;



}
