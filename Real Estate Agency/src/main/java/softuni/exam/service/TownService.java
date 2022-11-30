package softuni.exam.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface TownService {

    boolean areImported();

    String readTownsFileContent() throws IOException;
	
	String importTowns() throws IOException;
}

