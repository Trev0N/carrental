package w58984.carrental.service;

import org.hibernate.type.descriptor.java.OffsetDateTimeJavaDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import w58984.carrental.model.DTO.Car.CarCreateDTO;
import w58984.carrental.model.DTO.Car.CarDTO;
import w58984.carrental.model.DTO.Car.CarReadyDTO;
import w58984.carrental.model.DTO.Car.CarUpdateDTO;
import w58984.carrental.model.entity.Car;
import w58984.carrental.model.entity.CarDetail;
import w58984.carrental.model.entity.Garage;
import w58984.carrental.model.entity.User;
import w58984.carrental.model.entity.enums.StatusEnum;
import w58984.carrental.repository.CarDetailRepository;
import w58984.carrental.repository.CarRepository;
import w58984.carrental.repository.UserRepository;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {
    private CarRepository carRepository;
    private UserRepository userRepository;
    private CarDetailRepository carDetailRepository;


    @Autowired
    public CarService (UserRepository userRepository,CarRepository carRepository,
                       CarDetailRepository carDetailRepository){
        this.userRepository=userRepository;
        this.carRepository=carRepository;
        this.carDetailRepository=carDetailRepository;
    }

    public Optional<Car> findById(Long id){
        return carRepository.findById(id);
    }

    public List<CarDTO> getAllMyCars(Principal principal){
        authenticationAdmin();
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

    public List<CarReadyDTO> getAllReady(){

        return  carDetailRepository.findAllByStatusEnum(StatusEnum.READY_TO_RENT).stream().map(
                row -> new CarReadyDTO(
                        row.getCar().getId(),
                        row.getCar().getRegisterName(),
                        row.getCar().getMark(),
                        row.getCar().getModel(),
                        row.getCar().getEngine(),
                        row.getCar().getPower(),
                        row.getCar().getGarage().getId(),
                        row.getPrice()


                )).collect(Collectors.toList());

    }
    public void authenticationAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLEADMIN"));
        if (!hasUserRole) {
            throw new IllegalArgumentException("You don't have permission");
        }
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

    public void delete(Long id, Principal principal) throws IllegalAccessException {
        User user = userRepository.findByLogin(principal.getName());

       if(carRepository.getByIdAndUser(id, user)==null)
           throw new IllegalAccessException("You can not delete not your's car, or your car isn't exists");


        Car car = carRepository.getById(id);
        CarDetail carDetail = carDetailRepository.getByCar_Id(id);
        carDetailRepository.delete(carDetail);
        carRepository.delete(car);
    }
}
