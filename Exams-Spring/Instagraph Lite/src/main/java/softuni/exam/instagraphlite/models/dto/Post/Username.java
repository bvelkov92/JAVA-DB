package softuni.exam.instagraphlite.models.dto.Post;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Username {

    @XmlElement(name = "username")
    private String name;

}
