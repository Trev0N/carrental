package w58984.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import w58984.carrental.model.entity.CarDetail;


public interface CarDetailRepository extends JpaRepository<CarDetail,Long> {
    CarDetail getByCar_Id(Long id);


}
