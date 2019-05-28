package w58984.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import w58984.carrental.model.entity.Rent;
import w58984.carrental.model.entity.User;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent,Long> {

    Rent getByIdAndUser(Long id, User user);
    List<Rent> findAllByUser(User user);
 //TODO




}
