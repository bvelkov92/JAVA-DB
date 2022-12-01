package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.User.UserImportDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static softuni.exam.instagraphlite.constants.Messages.INVALID_USER;
import static softuni.exam.instagraphlite.constants.Messages.SUCCESSFULLY_ADDED_USER_MSG;
import static softuni.exam.instagraphlite.constants.Paths.GET_USERS_FROM_FILE;

@Getter
@Setter
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final PostRepository postRepository;

    public UserServiceImpl(UserRepository userRepository, PictureRepository pictureRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;

        this.postRepository = postRepository;
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(GET_USERS_FROM_FILE));
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(this.gson.fromJson(readFromFileContent(), UserImportDto[].class))
                .forEach(current -> {
                    boolean isValid = validationUtils.isValid(current);
                    boolean doesExistUser = false;
                    boolean doesExistPicture = true;
                    if (this.userRepository.findFirstByUsername(current.getUsername()).isPresent()) {
                        doesExistUser = true;
                    }
                    if (this.pictureRepository.findFirstByPath(current.getProfilePicture()).isEmpty()) {
                        doesExistPicture = false;
                    }

                    if (!doesExistPicture || doesExistUser) {
                        isValid = false;
                    }

                    if (isValid) {
                        sb.append(String.format(SUCCESSFULLY_ADDED_USER_MSG, current.getUsername()))
                                .append(System.lineSeparator());
                        User user = this.modelMapper.map(current, User.class);
                        Picture picture = this.pictureRepository.findFirstByPath(current.getProfilePicture()).get();
                        user.setProfilePicture(picture);
                        this.userRepository.saveAndFlush(user);
                    } else {
                        sb.append(INVALID_USER).append(System.lineSeparator());
                    }

                });

        return sb.toString();
    }

    @Override
        public String exportUsersWithTheirPosts() {

            StringBuilder sb = new StringBuilder();

            List<Post> posts = postRepository.findAll();

            posts.forEach(post -> {
                String username = post.getUser().getUsername();
                List<Post> userPosts = postRepository.findAllByUser_Username(username);

                sb.append(String.format("User: %s \n" +
                           " Post count: %d\n", post.getUser().getUsername()
                        , userPosts.size()));

                userPosts.forEach(userPost -> {
                    sb.append(String.format( " ==Post Details: \n"+
                                "----Caption: %s \n"+
                                    "----Picture Size: %.2f\n", userPost.getCaption(), userPost.getPicture().getSize()));
                });
            });

            return sb.toString();

    }
}
