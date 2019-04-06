package w58984.carrental.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w58984.carrental.model.DTO.CarDetail.CarDetailCreateDTO;
import w58984.carrental.model.entity.Car;
import w58984.carrental.model.entity.CarDetail;
import w58984.carrental.repository.CarDetailRepository;
import w58984.carrental.repository.CarRepository;
import w58984.carrental.repository.UserRepository;

import java.util.Optional;

@Service
public class CarDetailService {

    private CarRepository carRepository;
    private CarDetailRepository carDetailRepository;

    @Autowired
    public CarDetailService (CarRepository carRepository, CarDetailRepository carDetailRepository){
        this.carRepository=carRepository;
        this.carDetailRepository=carDetailRepository;
    }

    public void create(CarDetailCreateDTO api){
        Car car = carRepository.getById(api.getCarId());
        if (carDetailRepository.getByCar_Id(api.getCarId()) != null) {
            throw new IllegalArgumentException("Car detail exists for this car");
        }

        CarDetail carDetail = CarDetail.builder()
                                .car(car)
                                .price(api.getPrice())
                                .statusEnum(api.getStatusEnum())
                                .build();
        carDetailRepository.save(carDetail);
    }




}
