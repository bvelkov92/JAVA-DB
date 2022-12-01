package softuni.exam.instagraphlite.models.dto.Post;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class PicturePath {

    @XmlElement(name = "path")
    private String path;
}
