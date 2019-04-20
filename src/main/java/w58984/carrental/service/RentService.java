package w58984.carrental.service;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w58984.carrental.model.DTO.Car.CarDTO;
import w58984.carrental.model.DTO.Car.CarRentDTO;
import w58984.carrental.model.DTO.Rent.RentCreateDTO;
import w58984.carrental.model.DTO.Rent.RentDTO;
import w58984.carrental.model.entity.Car;
import w58984.carrental.model.entity.CarDetail;
import w58984.carrental.model.entity.Rent;
import w58984.carrental.model.entity.User;
import w58984.carrental.model.entity.enums.StatusEnum;
import w58984.carrental.repository.CarDetailRepository;
import w58984.carrental.repository.CarRepository;
import w58984.carrental.repository.RentRepository;
import w58984.carrental.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.OffsetDateTime.now;

@Service
public class RentService {

    private CarRepository carRepository;
    private UserRepository userRepository;
    private RentRepository rentRepository;
    private CarDetailRepository carDetailRepository;


    @Autowired
    public RentService (UserRepository userRepository,CarRepository carRepository, RentRepository rentRepository, CarDetailRepository carDetailRepository){
        this.userRepository=userRepository;
        this.carRepository=carRepository;
        this.rentRepository=rentRepository;
        this.carDetailRepository=carDetailRepository;
    }

    public List<RentDTO> getAll(Principal principal){
        User user = userRepository.findByLogin(principal.getName());
        return rentRepository.findAllByUser(user).stream().map(
                row-> new RentDTO(
                        row.getId(),
                        row.getRentStartDate(),
                        row.getRentEndDate(),
                        row.getCar().getId(),
                        row.getCar().getRegisterName(),
                        row.getCar().getMark(),
                        row.getCar().getModel(),
                        row.getCar().getEngine(),
                        row.getCar().getPower(),
                        row.getCar().getGarage().getName(),
                        row.getCar().getGarage().getAddress()

                )).collect(Collectors.toList());

    }



    public void create(RentCreateDTO api, Principal principal){
        User user = userRepository.findByLogin(principal.getName());
        Car car =carRepository.findById(api.getIdCar()).get();

        Preconditions.checkNotNull(carDetailRepository.getByCar_Id(api.getIdCar()),"Car isn't ready to rent");


        if(carDetailRepository.getByCar_Id(api.getIdCar()).getStatusEnum().equals(StatusEnum.READY_TO_RENT)) {
            Rent rent = Rent.builder()
                    .car(car)
                    .user(user)
                    .rentEndDate(api.getRentEndDate())
                    .build();
            rentRepository.save(rent);
            CarDetail carDetail = carDetailRepository.getByCar_Id(api.getIdCar());
            carDetail.setStatusEnum(StatusEnum.RENTED);
            carDetailRepository.save(carDetail);
        } else
            throw new IllegalArgumentException("Car is not for rent");

    }

    public void delete(Long id, Principal principal){
        User user = userRepository.findByLogin(principal.getName());
        if(rentRepository.getByIdAndUser(id,user)==null)
            throw new IllegalArgumentException("Bad rent_id");
        Rent rent= rentRepository.getByIdAndUser(id, user);
        CarDetail carDetail = carDetailRepository.getByCar_Id(id);
       rent.setRentEndDate(now());
       carDetail.setStatusEnum(StatusEnum.NEED_ATTENTION);
       rentRepository.save(rent);
       carDetailRepository.save(carDetail);

    }



}
