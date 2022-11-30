package softuni.exam.service.impl;


import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.Apartment.ApartmentWrapperDTO;
import softuni.exam.models.dto.Apartment.ImportApartmentDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParserForApartments;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static softuni.exam.config.constants.Messages.INVALID_APARTMENT_MSG;
import static softuni.exam.config.constants.Messages.SUCCESSFUL_ADDED_APARTMENT_MSG;
import static softuni.exam.config.constants.Paths.GET_APARTMENTS_FROM_FILE;

@Service
@Getter
@Setter
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final TownRepository townRepository;
    private final XmlParserForApartments xmlParserForApartments;


    public ApartmentServiceImpl(ApartmentRepository apartmentRepository,
                                ModelMapper modelMapper,
                                ValidationUtils validationUtils,
                                TownRepository townRepository,
                                XmlParserForApartments xmlParserForApartments) {
        this.apartmentRepository = apartmentRepository;

        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.townRepository = townRepository;
        this.xmlParserForApartments = xmlParserForApartments;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(GET_APARTMENTS_FROM_FILE.toPath());
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();

        ApartmentWrapperDTO apartmentWrapperDTO = xmlParserForApartments.fromFile(GET_APARTMENTS_FROM_FILE.toPath(),
                ApartmentWrapperDTO.class);
        List<ImportApartmentDTO> apartmentDTOList = apartmentWrapperDTO.getApartment();

        for (ImportApartmentDTO apartment : apartmentDTOList) {
            boolean isValid = this.validationUtils.isValid(apartment);


              if (this.apartmentRepository.findApartmentByAreaAndTown(apartment.getArea()
                      ,this.townRepository.findByTownName(apartment.getTown()).get()).isPresent()){
                  isValid = false;
              }


            if (isValid) {
                stringBuilder.append(String.format(SUCCESSFUL_ADDED_APARTMENT_MSG,
                                apartment.getApartmentType(), apartment.getArea()))
                        .append(System.lineSeparator());

                Town refTown = this.townRepository.findByTownName(apartment.getTown()).get();
                Apartment apartmentToSave = this.modelMapper.map(apartment, Apartment.class);
                apartmentToSave.setTown(refTown);


                this.apartmentRepository.saveAndFlush(apartmentToSave);


                stringBuilder.append(String.format(SUCCESSFUL_ADDED_APARTMENT_MSG,
                        apartment.getApartmentType(),
                        apartment.getArea()));
            } else {
                stringBuilder.append(INVALID_APARTMENT_MSG).append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }
}


