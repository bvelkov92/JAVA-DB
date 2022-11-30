package softuni.exam.models.dto;

import lombok.*;
import softuni.exam.models.entity.City;
import softuni.exam.util.DaysOfWeek;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "forecast")
public class ForecastImportDTO {

    @DecimalMax(value= "40")
    @DecimalMin(value= "-50")
    @NotNull
    @XmlElement(name = "min_temperature")
    private Double minTemperature;


    @DecimalMax(value= "60")
    @DecimalMin(value= "-20")
    @NotNull
    @XmlElement(name = "max_temperature")
    private Double maxTemperature;


    @NotNull
    @XmlElement
    private String sunset;

    @NotNull
    @XmlElement
    private String sunrise;

   @NotNull
   @XmlElement
    private Long city;

    @NotNull
    @XmlElement(name = "day_of_week")
    private DaysOfWeek dayOfWeek;

}
