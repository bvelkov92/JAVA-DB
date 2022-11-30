package com.example.football.models.dto.Players;

import com.example.football.constants.PositionPlayerEnum;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;


@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "player")
public class PlayerImportDto {

    @NotNull
    @XmlElement(name = "first-name")
    @Size(min = 3)
    private String firstName;

    @NotNull
    @XmlElement(name = "last-name")
    @Size(min = 3)
    private String lastName;

    @NotNull
    @Email(regexp = ".+[@].+[\\\\.].+")
    @XmlElement(name = "email")
    private String email;

    @NotNull
    @XmlElement(name = "birth-date")
    private String birthDate;


    @XmlElement(name = "position")
    private PositionPlayerEnum position;

    @XmlElement(name = "town")
    private TownName towns;

    @XmlElement(name = "team")
    private TeamName teams;

    @XmlElement(name = "stat")
    private StatId stat;
}
