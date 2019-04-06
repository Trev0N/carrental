package w58984.carrental.service;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import w58984.carrental.model.DTO.User.UserCreateDTO;
import w58984.carrental.model.entity.User;
import w58984.carrental.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void create(UserCreateDTO api) {
        String salt = BCrypt.gensalt();
        String encryptedPassword = BCrypt.hashpw(api.getPassword(), salt);

        User userExists = userRepository.findByLogin(api.getLogin());
        if(!(userExists ==null))
        throw new IllegalArgumentException("Your login is busy");

        User user = User.builder()
                .firstName(api.getFirstName())
                .lastName(api.getFirstName())
                .mail(api.getMail())
                .login(api.getLogin())
                .password(encryptedPassword)
                .role(User.Role.A)
                .status(User.Status.A)
                .salt(salt)
                .build();
        userRepository.save(user);
    }

}