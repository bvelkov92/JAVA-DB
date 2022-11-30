package softuni.exam.models.dto.Offer;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "offer")
public class OfferImportDTO {

    @XmlElement(name = "price")
    @Positive
    private BigDecimal price;

    @XmlElement(name = "publishedOn")
    private String publishedOn;


    @XmlElement(name = "apartment")
    private ApartmentId apartment;

    @XmlElement(name = "agent")
    private AgentName agent;

}
