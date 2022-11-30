package softuni.exam.models.dto.Agent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentDTO {

    @Size(min = 2)
   private  String firstName;
    @Size(min = 2)
    private  String lastName;

     private String town;

    @Email(regexp = ".+[@].+[\\\\.].+")
     private  String email;
}
