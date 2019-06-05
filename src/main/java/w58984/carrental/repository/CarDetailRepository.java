package w58984.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import w58984.carrental.model.entity.CarDetail;
import w58984.carrental.model.entity.User;
import w58984.carrental.model.entity.enums.StatusEnum;

import java.util.List;

/**
 * Interfejs z metodami pobierającymi dane z bazy danych dla klasy CarDetail
 */
public interface CarDetailRepository extends JpaRepository<CarDetail,Long> {
    CarDetail getByCar_Id(Long id);
    List<CarDetail> findAllByStatusEnum(StatusEnum statusEnum);
    List<CarDetail> findAllByCar_User(User user);



}
