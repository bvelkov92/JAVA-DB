package softuni.exam.service.impl;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ForecastImportDTO;
import softuni.exam.models.dto.ForecastWrapperDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static softuni.exam.config.Paths.GET_FORECASTS_PATH;

@Service
@Getter
@Setter

public class ForecastServiceImpl implements ForecastService {
    private final ForecastRepository forecastRepository;
    private final ValidationUtils validationUtils;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final CityRepository cityRepository;

    @Autowired
    public ForecastServiceImpl(ForecastRepository forecastRepository, ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper, XmlParser xmlParser, CityRepository cityRepository) {
        this.forecastRepository = forecastRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.cityRepository = cityRepository;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count()>0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(GET_FORECASTS_PATH.toPath());
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();

        ForecastWrapperDTO forecastWrapperDTO = xmlParser.fromFile(GET_FORECASTS_PATH, ForecastWrapperDTO.class);
        List<ForecastImportDTO> forecasts = forecastWrapperDTO.getForecast();
        for (ForecastImportDTO forecast : forecasts){
            boolean isValid = this.validationUtils.isValid(forecast);
            if (isValid) {
                if (this.forecastRepository.findFirstById(forecast.getCity()).isPresent()){
                    City refCity = this.cityRepository.findFirstById(forecast.getCity()).get();
                    Forecast castToSave = this.modelMapper.map(forecast, Forecast.class);
                    castToSave.setCity(refCity);
                    castToSave.setSunrise(LocalTime.parse(forecast.getSunrise(), DateTimeFormatter.ofPattern("HH:mm:ss")));
                    castToSave.setSunset(LocalTime.parse(forecast.getSunset(), DateTimeFormatter.ofPattern("HH:mm:ss")));

                    this.forecastRepository.saveAndFlush(castToSave);

                }

                //stringBuilder.append(String.format("Succesfully imported forecast %s - %d", forecast.ge))


            }else {
                stringBuilder.append("Invalid forecast").append(System.lineSeparator());
            }

        }


        return null;
    }

    @Override
    public String exportForecasts() {
        return null;
    }
}
