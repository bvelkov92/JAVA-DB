package softuni.exam.models.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "country")

public class Country extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String countryName;

    @Column(nullable = false)
    private String currency;


}


