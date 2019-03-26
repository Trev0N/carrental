package w58984.carrental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w58984.carrental.model.DTO.Car.CarCreateDTO;
import w58984.carrental.model.entity.Car;
import w58984.carrental.repository.CarRepository;

@Service
public class CarService {
    private CarRepository carRepository;

    @Autowired
    public CarService (CarRepository carRepository){
        this.carRepository=carRepository;
    }


    public void create(CarCreateDTO api){
        Car car = Car.builder()
                .mark(api.getMark())
                .model(api.getModel())
                .engine(api.getEngine())
                .power(api.getPower())
                .build();
        carRepository.save(car);
    }


}
