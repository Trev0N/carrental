package w58984.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import w58984.carrental.model.DTO.Car.CarRentDTO;
import w58984.carrental.model.entity.Car;
import w58984.carrental.model.entity.User;

import java.time.OffsetDateTime;
import java.util.List;
/**
 * Interfejs z metodami pobierającymi dane z bazy danych dla klasy Car
 */
public interface CarRepository extends JpaRepository<Car,Long> {


    Car findByRegisterNameAndUser(String name, User user);

    List<Car> findByUser(User user);

    Car getById(Long id);

    Car getByIdAndUser(Long id, User user);
   // List<CarRentDTO> findAllById(Long id);



}
