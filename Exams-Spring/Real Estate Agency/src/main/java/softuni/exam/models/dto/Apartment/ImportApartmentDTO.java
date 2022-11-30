package softuni.exam.models.dto.Apartment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.config.constants.ApartmentType;
import softuni.exam.models.entity.Town;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "apartment")
public class ImportApartmentDTO {

    @NotNull
    @XmlElement(name = "apartmentType")
    private ApartmentType apartmentType;

    @XmlElement(name = "area")
    @NotNull
    @DecimalMin(value = "40")
    private Double area;

    @NotNull
    @XmlElement(name ="town")
    private String town;
}
