package w58984.carrental.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import w58984.carrental.model.entity.User;
import w58984.carrental.repository.UserRepository;

@Service
public class MyUserDetails implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public MyUserDetails(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByLogin(username);

        if(user == null){
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE" + (user.getRole().equals("A") ? "ADMIN" : "USER"));
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities(authority)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }


}