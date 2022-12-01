package exam.model.entity;

import exam.constants.WarrantyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Table(name = "laptop")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Laptop extends BaseEntity {

    @Column(name = "mac_address", nullable = false, unique = true)
    @Size(min = 9)
    private String macAddress;

    @Column(name = "cpu_speed", nullable = false)
    @Positive
    private Double cpuSpeed;

    @Column(nullable = false)
    @Min(8)
    @Max(128)
    private Integer ram;

    @Column(nullable = false)
    @Min(128)
    @Max(1024)
    private Integer	storage;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(min = 10)
    private String description;

    @Column(nullable = false)
    @Positive
    private BigDecimal price;

    @Column(name = "warranty_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private WarrantyType  warrantyType;

    @ManyToOne
    private Shop shop;
}
