package w58984.carrental.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w58984.carrental.model.DTO.CarDetail.CarDetailCreateDTO;
import w58984.carrental.model.DTO.CarDetail.CarDetailDTO;
import w58984.carrental.model.DTO.CarDetail.CarDetailUpdateDTO;
import w58984.carrental.model.entity.Car;
import w58984.carrental.model.entity.CarDetail;
import w58984.carrental.model.entity.User;
import w58984.carrental.repository.CarDetailRepository;
import w58984.carrental.repository.CarRepository;
import w58984.carrental.repository.UserRepository;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Klasa obsługująca endpointy dotyczące szczegółów samochodów w serwisie
 */
@Service
public class CarDetailService {

    private CarRepository carRepository;
    private CarDetailRepository carDetailRepository;
    private CarService carService;
    private UserRepository userRepository;

    @Autowired
    public CarDetailService (CarRepository carRepository, CarDetailRepository carDetailRepository,
                             CarService carService, UserRepository userRepository){
        this.carRepository=carRepository;
        this.carDetailRepository=carDetailRepository;
        this.carService=carService;
        this.userRepository=userRepository;
    }

    /**
     * <p>
     * Metoda tworząca nowe szczegóły pojazdu w serwisie
     * </p>
     * @param api Dane do utworzenia szczegółów pojazdu
     */
    public void create(CarDetailCreateDTO api){
        Car car = carRepository.getById(api.getCarId());
        if (carDetailRepository.getByCar_Id(api.getCarId()) != null) {
            throw new IllegalArgumentException("Car detail exists for this car");
        }

        CarDetail carDetail = CarDetail.builder()
                                .car(car)
                                .price(api.getPrice())
                                .statusEnum(api.getStatusEnum())
                                .mileage(api.getMileage())
                                .build();
        carDetailRepository.save(carDetail);
    }
    /**
     * <p>
     * Metoda aktualizująca szczegóły pojazdu w serwisie
     * </p>
     * @param api Dane do zaktualizowania szczegółów pojazdu
     */
    public CarDetailUpdateDTO updateDetails(CarDetailUpdateDTO api){
        CarDetail carDetail = carDetailRepository.getByCar_Id(api.getCarId());
        if(carDetail==null)
            throw new IllegalArgumentException("Bad car_id");
        if (api.getMileage() != null) {
            carDetail.setMileage(api.getMileage());
        }
        if (api.getPrice() != null) {
            carDetail.setPrice(api.getPrice());
        }
        if (!(api.getStatusEnum() ==carDetailRepository.getByCar_Id(api.getCarId()).getStatusEnum())) {
            carDetail.setStatusEnum(api.getStatusEnum());
        }
        carRepository.getById(api.getCarId()).setModifiedAt(OffsetDateTime.now());
        carDetailRepository.save(carDetail);
        return CarDetailUpdateDTO.builder()
                .carId(api.getCarId())
                .mileage(carDetail.getMileage())
                .price(carDetail.getPrice())
                .statusEnum(carDetail.getStatusEnum())
                .build();
    }

    /**
     * <p>
     * Metoda szukająca detale wszystkich samochodów użytkownika
     * </p>
     * @param principal Informacje o użytkowniku wywołującym metode
     * @return Liste detali samochodów tylko dla użytkownika wywołującego metode (klasa CarDetailDTO)
     */
    public List<CarDetailDTO> getAllMyCarsDetails(Principal principal) {
        carService.authenticationAdmin();
        User user = userRepository.findByLogin(principal.getName());
        return carDetailRepository.findAllByCar_User(user).stream().map(
                row -> new CarDetailDTO().builder()
                .carId(row.getCar().getId())
                .mileage(row.getMileage())
                .price(row.getPrice())
                .statusEnum(row.getStatusEnum())
                .build()).collect(Collectors.toList());
    }
}
