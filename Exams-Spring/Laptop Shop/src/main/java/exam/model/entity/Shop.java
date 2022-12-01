package exam.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Table(name = "shop")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shop extends BaseEntity{

    @Column(nullable = false, unique = true)
    @Size(min = 4)
    private String name;

    @Column(nullable = false)
    @DecimalMin("20000")
    private BigDecimal income;

    @Column(nullable = false)
    @Size(min = 4)
    private String address;

    @Column(nullable = false,name ="employee_count")
    @Min(1)
    @Max(50)
    private Integer employeeCount;

    @Column(name = "shop_area", nullable = false)
    @Min(150)
    private Integer shopArea;

    @ManyToOne
    private Town towns;
}
