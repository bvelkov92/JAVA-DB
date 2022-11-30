package com.example.football.service.impl;

import com.example.football.constants.PositionPlayerEnum;
import com.example.football.models.dto.Players.PlayerImportDto;
import com.example.football.models.dto.Players.PlayerWrapperDto;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtils;
import com.example.football.util.XmlParser;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.example.football.constants.Paths.PATH_TO_PLAYER_FILE;
import static com.example.football.constants.Prints.INVALID_PLAYER_MSG;
import static com.example.football.constants.Prints.SUCCESS_ADD_PLAYER_MSG;

@Service
@Getter
@Setter
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final StatRepository statRepository;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper modelMapper, StatRepository statRepository, TownRepository townRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.statRepository = statRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
    }


    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(PATH_TO_PLAYER_FILE.toPath());
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {

        StringBuilder stringBuilder = new StringBuilder();
        PlayerWrapperDto playerWrapperDto = xmlParser.fromFile(PATH_TO_PLAYER_FILE.toPath(), PlayerWrapperDto.class);

        List<PlayerImportDto> playerImporList = playerWrapperDto.getPlayer();

        playerImporList.forEach(current -> {

            boolean isValid = this.validationUtils.isValid(current);

            if (this.playerRepository.findByEmail(current.getEmail()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                stringBuilder.append(String.format(SUCCESS_ADD_PLAYER_MSG, current.getFirstName(), current.getLastName(), current.getPosition()))
                        .append(System.lineSeparator());

                Player player = this.modelMapper.map(current, Player.class);
                Town town = this.townRepository.findFirstByName(current.getTowns().getTown()).get();
                Team team = this.teamRepository.findByName(current.getTeams().getTeam()).get();
                Stat stat = this.statRepository.findFirstById(current.getStat().getId()).get();
                LocalDate birthDay = ConvertStringToLocalDate(current.getBirthDate());
                player.setBirthDate(birthDay);
                player.setTowns(town);
                player.setTeams(team);
                player.setStats(stat);

                System.out.println();

                this.playerRepository.saveAndFlush(player);
            } else {
                stringBuilder.append(INVALID_PLAYER_MSG).append(System.lineSeparator());
            }
        });

        return stringBuilder.toString();
    }

    @Override
    public String exportBestPlayers() {

        StringBuilder sb = new StringBuilder();

        List<Player> bestPlayers = this.playerRepository.findBestPlayers(LocalDate.of(1995, 01, 01), LocalDate.of(2003, 01, 01));
        bestPlayers.forEach(p -> {

            String currentPlayer = String.format("Player - %s %s \n"+
                    "  Position - %s \n"+
                    "  Team - %s \n"+
                    "  Stadium - %s \n"+
                           " . . . ",p.getFirstName(),p.getLastName(),p.getPosition(),p.getTeams().getName(),p.getTeams().getStadiumName()
);
            sb.append(currentPlayer).append(System.lineSeparator());
        });

        return sb.toString();
    }


    public LocalDate ConvertStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String dates = date;

        //convert String to LocalDate
        return LocalDate.parse(date, formatter);
    }

}
