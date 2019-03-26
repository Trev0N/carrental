package w58984.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import w58984.carrental.model.entity.Car;

public interface CarRepository extends JpaRepository<Car,Long> {
}
