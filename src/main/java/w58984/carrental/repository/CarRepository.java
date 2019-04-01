package w58984.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import w58984.carrental.model.entity.Car;
import w58984.carrental.model.entity.User;

import java.time.OffsetDateTime;
import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {


    Car findByRegisterNameAndUser(String name, User user);

    List<Car> findByUser(User user);




}
