package com.example.football.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.attoparser.dom.Text;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Table(name = "towns")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Town extends BaseEntity{

    @Column(nullable = false, unique = true, name = "town_name")
    @Size(min = 2)
    private String name;

    @Column(nullable = false)
    private Long population;

    @Column(columnDefinition = "TEXT", name = "travel_guide", nullable = false)
    private String travelGuide;




}
