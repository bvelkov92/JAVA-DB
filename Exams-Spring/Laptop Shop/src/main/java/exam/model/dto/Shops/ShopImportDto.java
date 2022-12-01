package exam.model.dto.Shops;

import exam.model.entity.Town;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name ="shop")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopImportDto {

    @NotNull
    @Size(min = 4)
    @XmlElement(name ="name")
    private String name;

    @NotNull
    @DecimalMin("20000")
    @XmlElement(name = "income")
    private BigDecimal income;

    @NotNull
    @Size(min = 4)
    @XmlElement(name = "address")
    private String address;

    @NotNull
    @Min(1)
    @Max(50)
    @XmlElement(name ="employee-count")
    private Integer employeeCount;

    @NotNull
    @Min(150)
    @XmlElement(name = "shop-area")
    private Integer shopArea;

    @XmlElement(name = "town")
    @NotNull
    private TownName towns;
}
