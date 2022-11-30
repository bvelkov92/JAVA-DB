package com.example.football.service.impl;

import com.example.football.models.dto.Towns.TownsDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtils;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static com.example.football.constants.Paths.PATH_TO_TOWN_FILE;
import static com.example.football.constants.Prints.INVALID_TOWN_MSG;
import static com.example.football.constants.Prints.SUCCESS_ADD_TOWN_MSG;

@Service
@Getter
@Setter
public class TownServiceImpl implements TownService {

    private TownRepository townRepository;
    private final ValidationUtils validationUtils;
    private final Gson gson;
    private final ModelMapper modelMapper;


    public TownServiceImpl(TownRepository townRepository,ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper) {
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count()>0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(PATH_TO_TOWN_FILE));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readTownsFileContent(), TownsDTO[].class))
                .forEach(current->{
                    boolean isValid =this.validationUtils.isValid(current);

                    if (this.townRepository.findFirstByName(current.getName()).isPresent()) {
                        isValid=false;
                    }

                    if (isValid) {

                        stringBuilder
                                .append(String.format(SUCCESS_ADD_TOWN_MSG,current.getName(),current.getPopulation()))
                                .append(System.lineSeparator());

                        this.townRepository.saveAndFlush(modelMapper.map(current,Town.class));

                    }else {
                        stringBuilder.append(INVALID_TOWN_MSG).append(System.lineSeparator());
                    }
                });

        return stringBuilder.toString();
    }
}
