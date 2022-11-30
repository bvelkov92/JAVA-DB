package softuni.exam.models.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "city")
public class City extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String cityName;


    @Column
      private String description;

    @Column(nullable = false)
        private Long population;

    @ManyToOne
    private Country country;

}

