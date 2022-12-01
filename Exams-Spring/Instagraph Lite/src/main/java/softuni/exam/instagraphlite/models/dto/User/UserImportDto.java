package softuni.exam.instagraphlite.models.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.instagraphlite.models.entity.Picture;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserImportDto {

    @NotNull
    @Size(min = 2, max = 18)
    private String username;

    @NotNull
    @Size(min = 4)
    private String password;

    @NotNull
    private String profilePicture;
}
