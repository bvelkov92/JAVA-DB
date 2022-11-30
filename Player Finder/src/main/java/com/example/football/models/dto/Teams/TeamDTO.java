package com.example.football.models.dto.Teams;

import com.example.football.models.entity.Town;
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
public class TeamDTO {

    @Size(min = 3)
    private String name;

    @Size(min = 3)
    private String stadiumName;

    @Min(1000)
    private Long fanBase;

    @Size(min = 10)
    private String history;


    private String townName;

}
