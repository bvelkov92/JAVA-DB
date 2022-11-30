package softuni.exam.service.impl;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.Agent.AgentDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParserForApartments;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.config.constants.Messages.INVALID_AGENT_MSG;
import static softuni.exam.config.constants.Messages.SUCCESSFUL_ADDED_AGENT_MSG;
import static softuni.exam.config.constants.Paths.GET_AGENTS_FROM_FILE;

@Service
@Getter
@Setter
public class AgentServiceImpl implements AgentService {

    private AgentRepository agentRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private  final TownRepository townRepository;
    private final XmlParserForApartments xmlParserForApartments;


    public AgentServiceImpl(AgentRepository agentRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils, TownRepository townRepository, XmlParserForApartments xmlParserForApartments) {
        this.agentRepository = agentRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.townRepository = townRepository;
        this.xmlParserForApartments = xmlParserForApartments;
    }


    @Override
    public boolean areImported() {
        return this.agentRepository.count()>0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(GET_AGENTS_FROM_FILE));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readAgentsFromFile(), AgentDTO[].class))
                .forEach(current->{
                    boolean isValid = this.validationUtils.isValid(current);
                    if (this.agentRepository.findByFirstNameOrEmail(current.getFirstName(),current.getEmail()).isPresent()){
                        isValid =false;
                    }
                    if(isValid){
                        stringBuilder
                                .append(String.format(SUCCESSFUL_ADDED_AGENT_MSG,current.getFirstName(),current.getLastName()))
                                .append(System.lineSeparator());

                        Agent agent = modelMapper.map(current, Agent.class);

                        agent.setTown(this.townRepository.findByTownName(current.getTown()).get());


                            this.agentRepository.saveAndFlush(agent);

                    }else {
                        stringBuilder.append(INVALID_AGENT_MSG).append(System.lineSeparator());
                    }
                });
        return stringBuilder.toString();
    }
}
