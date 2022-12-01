package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.Picture.PictureImportDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static softuni.exam.instagraphlite.constants.Messages.INVALID_PICTURE;
import static softuni.exam.instagraphlite.constants.Messages.SUCCESSFULLY_ADDED_PICTURE_MSG;
import static softuni.exam.instagraphlite.constants.Paths.GET_PICTURES_FROM_FILE;

@Getter
@Setter
@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    public PictureServiceImpl(PictureRepository pictureRepository, UserRepository userRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count()>0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(GET_PICTURES_FROM_FILE));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson.fromJson(readFromFileContent(), PictureImportDto[].class))
                .forEach(current->{
                    boolean isValid = this.validationUtils.isValid(current);

                    if (this.pictureRepository.findFirstByPath(current.getPath()).isPresent()){
                        isValid=false;
                    }

                    if (isValid) {

                        sb.append(String.format(SUCCESSFULLY_ADDED_PICTURE_MSG,current.getSize()))
                                .append(System.lineSeparator());

                        Picture picture = this.modelMapper.map(current, Picture.class);
                        this.pictureRepository.saveAndFlush(picture);
                    }else {
                        sb.append(INVALID_PICTURE).append(System.lineSeparator());                    }

                });

        return sb.toString();
    }

    @Override
    public String exportPictures() {
        StringBuilder sb = new StringBuilder();
        this.pictureRepository.findAllBySizeGreaterThanEqualOrderBySizeAsc(30000.00)
                .forEach(current->  sb.append(exportPictureFormat(current.getSize(),current.getPath()))
                        .append(System.lineSeparator()));


        return sb.toString();
    }
    private String exportPictureFormat (Double size,String path){
        return  String.format("%.2f – %s \n" +
                ". . .", size,path
);

    }
}
