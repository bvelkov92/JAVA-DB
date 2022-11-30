package softuni.exam.service.impl;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCoutryDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


@Service
@Getter
@Setter

public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    private Object NoSuchElementException;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
      return Files.readString(Path.of("src/main/resources/files/json/countries.json"));

    }

    @Override
    public String importCountries() throws IOException {
        final  StringBuilder stringBuilder = new StringBuilder();

      Arrays.stream(gson.fromJson(readCountriesFromFile(), ImportCoutryDTO[].class))
                .forEach(countryDTO -> {
                    boolean isValid = this.validationUtils.isValid(countryDTO);
                    if (this.countryRepository.findFirstByCountryName(countryDTO.getCountryName()).isPresent()) {
                        isValid=false;
                    }

                        if (isValid) {
                            stringBuilder
                                    .append(String.format("Successfully imported country %s - %s",
                                            countryDTO.getCountryName(), countryDTO.getCurrency())).append(System.lineSeparator());

                            this.countryRepository.saveAndFlush(this.modelMapper.map(countryDTO,Country.class));

                        } else {
                            stringBuilder.append("Invalid country").append(System.lineSeparator());
                        }

                });

                return stringBuilder.toString() ;
    }
}
