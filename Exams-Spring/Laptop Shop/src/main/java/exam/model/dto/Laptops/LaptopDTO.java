package exam.model.dto.Laptops;

import exam.constants.WarrantyType;
import exam.model.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaptopDTO {

    @Size(min = 9)
    private String macAddress;

    @Positive
    private Double cpuSpeed;

    @Min(8)
    @Max(128)
    private Integer ram;

    @Min(128)
    @Max(1024)
    private Integer	storage;

    @Size(min = 10)
    private String description;

    @Positive
    private BigDecimal price;

    @Enumerated
    @NotNull
    private WarrantyType warrantyType;

    private ShopName shop;

}
