package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.config.constants.ApartmentType;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Table(name = "apartment")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Apartment extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApartmentType apartmentType;

    @Column(nullable = false)
    @Min(40)
    private Double area;

    @ManyToOne
    private  Town town;


}
