package w58984.carrental.service;

import org.hibernate.type.descriptor.java.OffsetDateTimeJavaDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w58984.carrental.model.DTO.Car.CarCreateDTO;
import w58984.carrental.model.DTO.Car.CarDTO;
import w58984.carrental.model.DTO.Car.CarUpdateDTO;
import w58984.carrental.model.entity.Car;
import w58984.carrental.model.entity.Garage;
import w58984.carrental.model.entity.User;
import w58984.carrental.repository.CarRepository;
import w58984.carrental.repository.UserRepository;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {
    private CarRepository carRepository;
    private UserRepository userRepository;

    @Autowired
    public CarService (UserRepository userRepository,CarRepository carRepository){
        this.userRepository=userRepository;
        this.carRepository=carRepository;
    }

    public Optional<Car> findById(Long id){
        return carRepository.findById(id);
    }

    public List<CarDTO> getAllMyCars(Principal principal){
        User user = userRepository.findByLogin(principal.getName());
        return carRepository.findByUser(user).stream().map(
                row -> new CarDTO(
                        row.getId(),
                        row.getRegisterName(),
                        row.getMark(),
                        row.getModel(),
                        row.getEngine(),
                        row.getPower(),
                        row.getGarage().getId()
                )).collect(Collectors.toList());


    }

    public List<CarDTO> getAll(){
        carRepository.findAll().toArray();
        return carRepository.findAll().stream().map(
                row -> new CarDTO(
                        row.getId(),
                        row.getRegisterName(),
                        row.getMark(),
                        row.getModel(),
                        row.getEngine(),
                        row.getPower(),
                        row.getGarage().getId()
                )).collect(Collectors.toList());

    }


    public void create(CarCreateDTO api,Principal principal){
        User user = userRepository.findByLogin(principal.getName());
        Optional.ofNullable(carRepository.findByRegisterNameAndUser(api.getRegisterName(),user)).ifPresent(
                e-> {
                    throw new IllegalArgumentException("Similar car exists");
                }
        );
        Garage garage = new Garage();
        garage.setId(api.getGarageId());

        Car car = Car.builder()
                .user(user)
                .registerName(api.getRegisterName())
                .mark(api.getMark())
                .model(api.getModel())
                .engine(api.getEngine())
                .power(api.getPower())
                .garage(garage)
                .build();
        carRepository.save(car);
    }

    public void update(Car car, CarUpdateDTO api, Principal principal){
        User user = userRepository.findByLogin(principal.getName());

        Optional.ofNullable(carRepository.findByRegisterNameAndUser(api.getRegisterName(),user)).ifPresent(
                e-> {
                    throw new IllegalArgumentException("Similar car exists");
                }
        );
        car = car.toBuilder()
                .registerName(api.getRegisterName())
                .mark(api.getMark())
                .model(api.getModel())
                .engine(api.getEngine())
                .power(api.getPower())
                .build();
        carRepository.save(car);
    }

    public void delete(Car car){
        carRepository.delete(car);
    }
}
