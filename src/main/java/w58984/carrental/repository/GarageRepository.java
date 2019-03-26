package w58984.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import w58984.carrental.model.entity.Garage;

public interface GarageRepository extends JpaRepository<Garage,Long> {
}
