package w58984.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import w58984.carrental.model.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

}
