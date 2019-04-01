package w58984.carrental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w58984.carrental.model.DTO.Car.CarDTO;
import w58984.carrental.model.DTO.Car.CarRentDTO;
import w58984.carrental.model.DTO.Rent.RentCreateDTO;
import w58984.carrental.model.entity.Car;
import w58984.carrental.model.entity.Rent;
import w58984.carrental.model.entity.User;
import w58984.carrental.repository.CarRepository;
import w58984.carrental.repository.RentRepository;
import w58984.carrental.repository.UserRepository;

import java.security.Principal;
import java.util.Optional;

@Service
public class RentService {

    private CarRepository carRepository;
    private UserRepository userRepository;
    private RentRepository rentRepository;


    @Autowired
    public RentService (UserRepository userRepository,CarRepository carRepository, RentRepository rentRepository){
        this.userRepository=userRepository;
        this.carRepository=carRepository;
        this.rentRepository=rentRepository;
    }


    public void create(RentCreateDTO api,CarRentDTO carRentDTO, Principal principal){
        User user = userRepository.findByLogin(principal.getName());
        Car car =carRepository.findById(carRentDTO.getId()).get();
        Rent rent = Rent.builder()
                .car(car)
                .user(user)
                .rentEndDate(api.getRentEndDate())
                .build();

    }


}
