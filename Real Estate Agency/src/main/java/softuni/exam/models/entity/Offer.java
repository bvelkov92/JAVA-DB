package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "offer")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Offer extends BaseEntity{

    @Column(nullable = false)
    @Positive
    private BigDecimal price;

    @Column(name = "published_on", nullable = false)
    private LocalDate publishedOn;

    @ManyToOne
    private Apartment apartments;

    @ManyToOne
    private Agent agents;

}
