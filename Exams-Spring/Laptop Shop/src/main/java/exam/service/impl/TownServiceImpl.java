package exam.service.impl;

import exam.model.dto.Towns.TownImportDTO;
import exam.model.dto.Towns.TownWrapperDTO;
import exam.model.entity.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationUtils;
import exam.util.XmlParser;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static exam.constants.Messages.INVALID_TOWN_MSG;
import static exam.constants.Messages.SUCCESSFUL_ADDED_TOWN_MSG;
import static exam.constants.Paths.GET_TOWNS_FROM_FILE;

@Service
@Getter
@Setter

public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count()>0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(GET_TOWNS_FROM_FILE.toPath());
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        TownWrapperDTO townWrapperList = xmlParser.fromFile(GET_TOWNS_FROM_FILE.toPath(), TownWrapperDTO.class);
        List<TownImportDTO> townImportList = townWrapperList.getTownImportDTO();

        townImportList.forEach(current->{
            boolean isValid = this.validationUtils.isValid(current);

            if(this.townRepository.findFirstByName(current.getName()).isPresent()){
                isValid = false;
            }
            if (isValid){
                sb.append(String.format(SUCCESSFUL_ADDED_TOWN_MSG, current.getName())).append(System.lineSeparator());
                this.townRepository.saveAndFlush(this.modelMapper.map(current, Town.class));
            }else {
                sb.append(INVALID_TOWN_MSG).append(System.lineSeparator());
            }
        });
        return sb.toString();
    }
}
