package softuni.exam.config;

import java.io.File;
import java.nio.file.Path;

public enum Paths {
    ;
    public static final File GET_FORECASTS_PATH = Path.of("src/main/resources/files/xml/forecasts.xml").toFile();
}
