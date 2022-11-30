package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Country;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ImportCitiesDTO {

    @Size(min = 2, max = 60)
    private String cityName;

    @Size(min = 2, max = 60)
    private String description;

    @Size(min = 500)
    private Long population;


    private Long country;
}
