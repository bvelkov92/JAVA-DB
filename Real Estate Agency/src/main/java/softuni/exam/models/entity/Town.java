package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@Table(name = "town")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Town extends BaseEntity{

    @Column(name = "town_name", nullable = false,unique = true)
    private String townName;

    @Column(nullable = false)
    private Integer population;

}
