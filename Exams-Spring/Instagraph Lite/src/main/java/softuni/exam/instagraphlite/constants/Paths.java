package softuni.exam.instagraphlite.constants;

import java.io.File;
import java.nio.file.Path;

public enum Paths {
    ;

    public static final String GET_PICTURES_FROM_FILE = "src/main/resources/files/pictures.json";
    public static final String GET_USERS_FROM_FILE = "src/main/resources/files/users.json";


    public  static final File GET_POSTS_FROM_FILE = Path.of("src/main/resources/files/posts.xml").toFile();
}
