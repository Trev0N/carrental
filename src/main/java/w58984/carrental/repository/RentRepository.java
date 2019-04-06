package w58984.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import w58984.carrental.model.entity.Rent;

public interface RentRepository extends JpaRepository<Rent,Long> {

}
