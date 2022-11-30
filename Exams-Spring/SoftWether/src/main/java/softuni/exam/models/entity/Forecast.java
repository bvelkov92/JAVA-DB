package softuni.exam.models.entity;

import lombok.*;
import softuni.exam.util.DaysOfWeek;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Table(name = "forecasts")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Forecast extends BaseEntity{

    @Column(name = "min_temperature", nullable = false)
    @Size(min = -50, max = 40)
    private Double minTemperature;

    @Column(nullable = false, name = "max_temperature")
    @Size(min = -20, max = 60)
    private Double maxTemperature;


    @Column(nullable = false)
    private LocalTime  sunset;

    @Column(nullable = false)
    private LocalTime sunrise;

    @ManyToOne
    private City city;

    @Column(nullable = false, name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DaysOfWeek dayOfWeek;

}
