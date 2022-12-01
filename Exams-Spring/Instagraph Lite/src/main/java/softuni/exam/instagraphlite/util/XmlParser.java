package softuni.exam.instagraphlite.util;

import org.springframework.stereotype.Component;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

@Component
public class XmlParser{

    public <T> T fromFile(Path filePatch, Class<T> object) throws JAXBException, FileNotFoundException {
        JAXBContext context =  JAXBContext.newInstance(object);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        FileReader fileReader = new FileReader(String.valueOf(filePatch));
        return (T) unmarshaller.unmarshal(fileReader);
    }
}
