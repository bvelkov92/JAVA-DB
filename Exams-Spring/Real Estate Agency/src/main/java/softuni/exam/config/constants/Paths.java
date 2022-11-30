package softuni.exam.config.constants;

import java.io.File;
import java.nio.file.Path;

public enum Paths {

    ;
    public static final String GET_TOWNS_FROM_FILE = "src/main/resources/files/json/towns.json";
    public static final String GET_AGENTS_FROM_FILE = "src/main/resources/files/json/agents.json";
    public  static final File GET_APARTMENTS_FROM_FILE = Path.of("src/main/resources/files/xml/apartments.xml").toFile();
    public  static final File GET_OFFERS_FROM_FILE = Path.of("src/main/resources/files/xml/offers.xml").toFile();
}
