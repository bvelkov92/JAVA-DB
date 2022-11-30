package softuni.exam.service.impl;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.Town.TownsDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.config.constants.Messages.INVALID_TOWN_MSG;
import static softuni.exam.config.constants.Messages.SUCCESSFUL_ADDED_TOWN_MSG;
import static softuni.exam.config.constants.Paths.GET_TOWNS_FROM_FILE;


@Service
@Getter
@Setter
public class TownServiceImpl implements TownService {

    private final ValidationUtils validationUtils;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;


    @Autowired
    public TownServiceImpl(ValidationUtils validationUtils, TownRepository townRepository, Gson gson, ModelMapper modelMapper) {
        this.validationUtils = validationUtils;
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count()>0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(GET_TOWNS_FROM_FILE));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readTownsFileContent(), TownsDTO[].class))
                .forEach(current-> {
                    boolean isValid = this.validationUtils.isValid(current);

                    if (this.townRepository.findByTownName(current.getTownName()).isPresent()) {
                        isValid = false;
                    }

                    if (isValid){
                        stringBuilder
                                .append(String.format(SUCCESSFUL_ADDED_TOWN_MSG,current.getTownName(),current.getPopulation()))
                                .append(System.lineSeparator());
                        this.townRepository.saveAndFlush(modelMapper.map(current, Town.class));

                    }else {
                        stringBuilder.append(INVALID_TOWN_MSG).append(System.lineSeparator());
                    }
                });
        return stringBuilder.toString();
    }
}
