package softuni.exam.instagraphlite.models.dto.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.User;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "post")
public class PostImportDto {

    @XmlElement(name = "caption")
    @Size(min = 21)
    @NotNull
    private String caption;

    @NotNull
    @XmlElement(name = "user")
    private Username user;

    @NotNull
    private PicturePath picture;
}
