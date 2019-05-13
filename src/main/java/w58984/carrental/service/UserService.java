package w58984.carrental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;
import w58984.carrental.model.DTO.User.UserCreateDTO;
import w58984.carrental.model.entity.User;
import w58984.carrental.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Service
public class UserService {
    private UserRepository userRepository;

    private final DefaultTokenServices defaultTokenServices;

    @Autowired
    public UserService(UserRepository userRepository, DefaultTokenServices defaultTokenServices) {
        this.userRepository = userRepository;
        this.defaultTokenServices = defaultTokenServices;
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
                .role(User.Role.U)
                .status(User.Status.A)
                .salt(salt)
                .build();
        userRepository.save(user);
    }

    public boolean deleteToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        authorization = authorization.replaceAll("Bearer ", "");
        return defaultTokenServices.revokeToken(authorization);
    }


    public boolean isAdmin(Principal principal){
        if(userRepository.findByLogin(principal.getName()).getRole()== User.Role.A)
        return true;
        else
            return false;
    }


}