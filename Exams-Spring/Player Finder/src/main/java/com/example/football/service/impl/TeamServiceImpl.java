package com.example.football.service.impl;

import com.example.football.models.dto.Teams.TeamDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
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

import static com.example.football.constants.Paths.PATH_TO_TEAM_FILE;
import static com.example.football.constants.Prints.INVALID_TEAM_MSG;
import static com.example.football.constants.Prints.SUCCESS_ADD_TEAM_MSG;


@Service
@Getter
@Setter
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private final ValidationUtils validationUtils;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private  final TownRepository townRepository;

    public TeamServiceImpl(TeamRepository teamRepository, ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper, TownRepository townRepository) {
        this.teamRepository = teamRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count()>0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(PATH_TO_TEAM_FILE));
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readTeamsFileContent(),TeamDTO[].class))
                .forEach(current->{
                    boolean isValid = this.validationUtils.isValid(current);

                    if (this.teamRepository.findByName(current.getName()).isPresent()) {
                        isValid =false;
                    }

                    if(isValid){
                        stringBuilder
                                .append(String.format(SUCCESS_ADD_TEAM_MSG,current.getName(),current.getFanBase()))
                                .append(System.lineSeparator());
                        Team team = this.modelMapper.map(current,Team.class);

                        team.setTowns(this.townRepository.findFirstByName(current.getTownName()).get());


                            this.teamRepository.saveAndFlush(team);
                    }else {
                        stringBuilder.append(INVALID_TEAM_MSG).append(System.lineSeparator());
                    }
                });

        return stringBuilder.toString();
    }
}
