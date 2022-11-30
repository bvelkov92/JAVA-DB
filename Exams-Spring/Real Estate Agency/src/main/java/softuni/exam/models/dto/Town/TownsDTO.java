package softuni.exam.models.dto.Town;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TownsDTO {
    @Size(min = 2)
    private String townName;
    @Min(1)
     private Integer population;

}
