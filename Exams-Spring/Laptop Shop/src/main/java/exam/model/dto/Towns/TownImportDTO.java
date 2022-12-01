package exam.model.dto.Towns;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

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
@XmlRootElement(name ="town")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownImportDTO {

    @NotNull
    @XmlElement(name = "name")
    @Size(min = 2)
    private String name;

    @XmlElement(name = "population")
    @Positive
    @NotNull
    private Integer population;

    @NotNull
    @XmlElement(name = "travel-guide")
    @Size(min = 10)
    private String travelGuide;
}
