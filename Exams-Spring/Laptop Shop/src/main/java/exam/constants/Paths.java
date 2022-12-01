package exam.constants;

import java.io.File;
import java.nio.file.Path;

public enum Paths {

    ;


    public static final String GET_CUTOMERS_FROM_FILE = "src/main/resources/files/json/customers.json";
    public static final String GET_LAPTOPS_FROM_FILE = "src/main/resources/files/json/laptops.json";


    public  static final File GET_SHOPS_FROM_FILE = Path.of("src/main/resources/files/xml/shops.xml").toFile();
    public  static final File GET_TOWNS_FROM_FILE = Path.of("src/main/resources/files/xml/towns.xml").toFile();
}
