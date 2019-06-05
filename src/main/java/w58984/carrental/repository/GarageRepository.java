package w58984.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import w58984.carrental.model.entity.Garage;
/**
 * Interfejs z metodami pobierającymi dane z bazy danych dla klasy Garage
 */
public interface GarageRepository extends JpaRepository<Garage,Long> {
    Garage findByName(String name);

}
