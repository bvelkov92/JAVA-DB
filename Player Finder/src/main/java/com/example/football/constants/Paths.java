package com.example.football.constants;


import java.io.File;
import java.nio.file.Path;

public enum Paths {
    ;

public static final File PATH_TO_PLAYER_FILE = Path.of("src/main/resources/files/xml/players.xml").toFile();
    public static final File PATH_TO_STATS_FILE = Path.of("src/main/resources/files/xml/stats.xml").toFile();
    public static String PATH_TO_TEAM_FILE = "src/main/resources/files/json/teams.json";
    public static String PATH_TO_TOWN_FILE = "src/main/resources/files/json/towns.json";

}
