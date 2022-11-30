package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Table(name = "agent")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agent  extends BaseEntity{

    @Column(name = "first_name", nullable = false, unique = true)

    private String firstName;
    @Column(name = "last_name", nullable = false, unique = true)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne
    private  Town town;

}
