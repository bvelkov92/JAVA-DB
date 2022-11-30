package softuni.exam.service.impl;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCitiesDTO;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtils;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


@Service
@Getter
@Setter
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final ValidationUtils validationUtils;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count()>0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/cities.json"));
    }

    @Override
    public String importCities() throws IOException {
      final  StringBuilder stringBuilder = new StringBuilder();

        List<City> cities = Arrays.stream(gson.fromJson(readCitiesFileContent(), ImportCitiesDTO[].class))
                .filter(cityDTO -> {
                    boolean isValid = this.validationUtils.isValid(cityDTO);

                    if(this.cityRepository.findFirstByCityName(cityDTO.getCityName()).isPresent()){
                        isValid=false;
                    }

                    if (isValid) {
                        stringBuilder.
                                append(String.format("Successfully imported city %s - %d",
                                        cityDTO.getCityName(), cityDTO.getPopulation()))
                                .append(System.lineSeparator());

                        this.cityRepository.saveAndFlush(this.modelMapper.map(cityDTO,City.class));
                    } else {
                        stringBuilder.append("Invalid city").append(System.lineSeparator());
                    }
                    return isValid;
                }).map(imp -> modelMapper.map(imp, City.class)).toList();


        return stringBuilder.toString();
    }
}
