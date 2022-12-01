package softuni.exam.instagraphlite.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.Post.PostImportDto;
import softuni.exam.instagraphlite.models.dto.Post.PostWrapperDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidationUtils;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static softuni.exam.instagraphlite.constants.Messages.INVALID_POST;
import static softuni.exam.instagraphlite.constants.Messages.SUCCESSFULLY_ADDED_POST_MSG;
import static softuni.exam.instagraphlite.constants.Paths.GET_POSTS_FROM_FILE;

@Getter
@Setter
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;


    public PostServiceImpl(PostRepository postRepository, PictureRepository pictureRepository, UserRepository userRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.postRepository = postRepository;
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(GET_POSTS_FROM_FILE.toPath());
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        PostWrapperDto postWrapperList = this.xmlParser.fromFile(GET_POSTS_FROM_FILE.toPath(), PostWrapperDto.class);

        List<PostImportDto> postImportList = postWrapperList.getPostWrapperList();

        postImportList.forEach(current -> {
            boolean isValid = validationUtils.isValid(current);

            if (
                    this.userRepository.findFirstByUsername(current.getUser().getName()).isEmpty() ||
                            this.pictureRepository.findFirstByPath(current.getPicture().getPath()).isEmpty()
            ) {
                isValid = false;
            }

            if(isValid){
                sb.append(String.format(SUCCESSFULLY_ADDED_POST_MSG,current.getUser().getName())).append(System.lineSeparator());

                Post post = this.modelMapper.map(current, Post.class);
                Picture picture = this.pictureRepository.findFirstByPath(current.getPicture().getPath()).get();
                User user = this.userRepository.findFirstByUsername(current.getUser().getName()).get();

                post.setPicture(picture);
                post.setUser(user);

                this.postRepository.saveAndFlush(post);
            }else {
                sb.append(INVALID_POST).append(System.lineSeparator());
            }

        });
        return sb.toString();
    }
}
