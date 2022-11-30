package com.example.football.service.impl;

import com.example.football.models.dto.Stats.StatImportDto;
import com.example.football.models.dto.Stats.StatWrapperDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtils;
import com.example.football.util.XmlParser;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static com.example.football.constants.Paths.PATH_TO_STATS_FILE;
import static com.example.football.constants.Prints.INVALID_STAT_MSG;
import static com.example.football.constants.Prints.SUCCESS_ADD_STAT_MSG;

@Service
@Getter
@Setter
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    public StatServiceImpl(StatRepository statRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(PATH_TO_STATS_FILE.toPath());
    }

    @Override
    public String importStats() throws JAXBException, FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();

        StatWrapperDto statWrapperDto = xmlParser.fromFile(PATH_TO_STATS_FILE.toPath(),
                StatWrapperDto.class);


        List<StatImportDto> importDTOList = statWrapperDto.getStat();

        for (StatImportDto current : importDTOList) {
            boolean isValid = this.validationUtils.isValid(current);

            if (this.statRepository.findFirstByEnduranceAndAndPassingAndAndShooting
                    (current.getEndurance(), current.getPassing(), current.getShooting()).isPresent()) {
                isValid = false;
            }

            if (isValid) {

                stringBuilder.append(String.format(SUCCESS_ADD_STAT_MSG, current.getPassing(), current.getShooting(), current.getEndurance()))
                        .append(System.lineSeparator());

                Stat statToSave = this.modelMapper.map(current,Stat.class);
                this.statRepository.saveAndFlush(statToSave);

            } else {
                stringBuilder.append(INVALID_STAT_MSG).append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }
}