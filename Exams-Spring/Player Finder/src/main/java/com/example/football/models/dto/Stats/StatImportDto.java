package com.example.football.models.dto.Stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "stat")
public class StatImportDto {

    @NotNull
    @Positive
    @XmlElement(name = "shooting")
    private Double shooting;

    @NotNull
    @Positive
    @XmlElement(name = "passing")
    private Double passing;

    @NotNull
    @Positive
    @XmlElement(name = "endurance")
    private Double endurance;
}
