package com.example.football.models.dto.Towns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TownsDTO {

    @Size(min = 2)
    private String name;
    @Min(1)
    private Long population;

    @Size(min = 10)
    private String travelGuide;

}
