package exam.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Table(name = "town")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Town extends BaseEntity{

    @Column(nullable = false, unique = true)
    @Size(min = 2)
    private String name;

    @Column(nullable = false)
    @Positive
    private Integer population;

    @Column(name = "travel_guide",nullable = false,columnDefinition = "TEXT")
    @Size(min = 10)
    private String travelGuide;

}
