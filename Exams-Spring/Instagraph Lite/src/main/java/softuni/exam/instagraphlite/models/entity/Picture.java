package softuni.exam.instagraphlite.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pictures")
public class Picture  extends BaseEntity{


    @Column(nullable = false,unique = true)
    private String path;

    @Column(nullable = false)
    private Double size;
}
