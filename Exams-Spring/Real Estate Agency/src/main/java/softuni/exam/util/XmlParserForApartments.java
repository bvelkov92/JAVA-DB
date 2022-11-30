package softuni.exam.util;

import org.springframework.stereotype.Component;
import softuni.exam.models.dto.Apartment.ApartmentWrapperDTO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;


@Component
public class XmlParserForApartments {
    public <T> T fromFile(Path filePatch, Class<ApartmentWrapperDTO> object) throws JAXBException, FileNotFoundException {
        JAXBContext context =  JAXBContext.newInstance(object);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        FileReader fileReader = new FileReader(String.valueOf(filePatch));
       return (T) unmarshaller.unmarshal(fileReader);
    }
}
