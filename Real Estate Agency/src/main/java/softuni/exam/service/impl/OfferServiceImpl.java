package softuni.exam.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.config.constants.ApartmentType;
import softuni.exam.models.dto.Offer.AgentName;
import softuni.exam.models.dto.Offer.ApartmentId;
import softuni.exam.models.dto.Offer.OfferImportDTO;
import softuni.exam.models.dto.Offer.OfferWraperDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParserForOffers;


import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static softuni.exam.config.constants.Messages.INVALID_OFFER_MSG;
import static softuni.exam.config.constants.Messages.SUCCESSFUL_ADDED_OFFER_MSG;
import static softuni.exam.config.constants.Paths.GET_OFFERS_FROM_FILE;

@Service
@Getter
@Setter
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    private final XmlParserForOffers xmlParserForOffers;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ValidationUtils validationUtils, ModelMapper modelMapper, XmlParserForOffers xmlParserForOffers, AgentRepository agentRepository, ApartmentRepository apartmentRepository) {
        this.offerRepository = offerRepository;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.xmlParserForOffers = xmlParserForOffers;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
    }


    @Override
    public boolean areImported() {
        return this.offerRepository.count()>0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(GET_OFFERS_FROM_FILE.toPath());
    }

    @Override
    public String importOffers() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();
        OfferWraperDTO offerWraperDTO = xmlParserForOffers.fromFile(GET_OFFERS_FROM_FILE.toPath(), OfferWraperDTO.class);
        List<OfferImportDTO> offerImportDTO = offerWraperDTO.getOffer();

        for (OfferImportDTO offer: offerImportDTO) {
            boolean isValid = this.validationUtils.isValid(offer);

            if (this.agentRepository.findFirstByFirstName(offer.getAgent().getName()).isEmpty()){
                isValid = false;
            }
            if (isValid) {
                Offer offerToSave = this.modelMapper.map(offer, Offer.class);
                    AgentName agentName = offer.getAgent();
                ApartmentId apId = offer.getApartment();

                Agent agent = this.agentRepository.findFirstByFirstName(agentName.getName()).get();
                Apartment apartment = this.apartmentRepository.findApartmentById(apId.getId()).get();
                offerToSave.setAgents(agent);
                LocalDate publishedDate = ConvertStringToLocalDate(offer.getPublishedOn());
                offerToSave.setPublishedOn(publishedDate);
                offerToSave.setApartments(apartment);
                sb.append(String.format(SUCCESSFUL_ADDED_OFFER_MSG,offer.getPrice())).append(System.lineSeparator());
                this.offerRepository.saveAndFlush(offerToSave);
            }else {

                sb.append(INVALID_OFFER_MSG).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    @Override
    public String exportOffers() {
        StringBuilder sb = new StringBuilder();
      List<Offer> offers = this.offerRepository
                .findAllByApartments_ApartmentTypeOrderByApartments_AreaDescPriceAsc(ApartmentType.three_rooms);


      offers.forEach(el ->{ sb.append(String.format("Agent %s %s with offer №%d:%n" +
                                "   -Apartment area: %.2f%n" +
                                "   --Town: %s%n" +
                                "   ---Price: %.2f$",
                        el.getAgents().getFirstName(),
                        el.getAgents().getLastName(),
                        el.getId(),
                        el.getApartments().getArea(),
                        el.getApartments().getTown().getTownName(),
                        el.getPrice()))
                .append(System.lineSeparator());
        });

        return sb.toString();
    }

public LocalDate ConvertStringToLocalDate(String date){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    String dates =  date;

    //convert String to LocalDate
    return  LocalDate.parse(date, formatter);
}
}