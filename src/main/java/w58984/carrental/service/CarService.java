package w58984.carrental.service;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import w58984.carrental.model.Api.car.CarEditApi;
import w58984.carrental.model.Api.car.CarCreateApi;
import w58984.carrental.model.DTO.Car.CarDTO;
import w58984.carrental.model.DTO.Car.CarReadyDTO;
import w58984.carrental.model.entity.Car;
import w58984.carrental.model.entity.CarDetail;
import w58984.carrental.model.entity.Garage;
import w58984.carrental.model.entity.User;
import w58984.carrental.model.entity.enums.StatusEnum;
import w58984.carrental.repository.CarDetailRepository;
import w58984.carrental.repository.CarRepository;
import w58984.carrental.repository.GarageRepository;
import w58984.carrental.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Klasa obsługująca endpointy dotyczące samochodów w serwisie
 */
@Service
public class CarService {
    private CarRepository carRepository;
    private UserRepository userRepository;
    private CarDetailRepository carDetailRepository;
    private GarageRepository garageRepository;


    @Autowired
    public CarService (UserRepository userRepository,CarRepository carRepository,
                       CarDetailRepository carDetailRepository, GarageRepository garageRepository){
        this.userRepository=userRepository;
        this.carRepository=carRepository;
        this.carDetailRepository=carDetailRepository;
        this.garageRepository = garageRepository;
    }

    /**
     * <p>
     *     Metoda szukająca samochodu według id
     * </p>
     * @param id ID pojazdu, który ma zostać znaleziony
     * @return Obiekt klasy Car
     */
    public Optional<Car> findById(Long id){
        return carRepository.findById(id);
    }

    /**
     * <p>
     *     Metoda szukająca wszystkie pojazdy, dla użytkowników z rolą administratora
     * </p>
     * @param principal Zmienna zawierająca dane aktualnie zalogowanego użytkownika
     * @return Liste samochodów (klasa CarDTO)
     */
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

    /**
     * <p>
     *     Metoda znajdująca wszystkie pojazdy w serwisie
     * </p>
     * @return Liste samochodów (klasa CarDTO)
     */
    public List<CarDTO> getAll(){
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

    /**
     * <p>
     *     Metoda szukająca wszystkie samochody gotowe do wypożyczenia
     * </p>
     * @return Liste samochodów (klasa CarReadyDTO)
     */
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

    /**
     * <p>
     *     Metoda weryfikująca czy użytkownik jest administratorem
     * </p>
     */
    public void authenticationAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLEADMIN"));
        if (!hasUserRole) {
            throw new IllegalArgumentException("You don't have permission");
        }
    }


    /**
     * <p>
     *     Metoda tworząca nowy pojazd w serwisie
     * </p>
     * @param api Dane do utworzenia pojazdu
     * @param principal Informacje o użytkowniku wywołującym metode
     */
    public void create(CarCreateApi api, Principal principal){
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

    /**
     * <p>
     *     Metoda aktualizująca pojazd w serwisie
     * </p>
     * @param id ID pojazdu
     * @param api Nowe dane dla pojazdu
     * @param principal Informacje o użytkowniku wywołującym metode
     */
    public void update(Long id,CarEditApi api, Principal principal){
        authenticationAdmin();
        Preconditions.checkNotNull(carRepository.getById(id),"Wrong car id. ");
        Preconditions.checkNotNull(garageRepository.getOne(api.getGarageId()),"Wrong garage id. ");
        User user = userRepository.findByLogin(principal.getName());
        if(carRepository.getById(id).getId()!=id)
        Optional.ofNullable(carRepository.findByRegisterNameAndUser(api.getRegisterName(),user)).ifPresent(
                e-> {
                    throw new IllegalArgumentException("Similar registerName exists");
                }

        );

       Car car = carRepository.getById(id);
        car = car.toBuilder()
                .registerName(api.getRegisterName())
                .mark(api.getMark())
                .model(api.getModel())
                .engine(api.getEngine())
                .power(api.getPower())
                .garage(garageRepository.getOne(api.getGarageId()))
                .build();
        carRepository.save(car);
    }

    /**
     * <p>
     *     Metoda usuwająca pojazd z serwisu
     * </p>
     * @param id ID pojazdu
     * @param principal Informacje o użytkowniku wywołującym metode
     * @throws IllegalAccessException
     */
    public void delete(Long id, Principal principal) throws IllegalAccessException {
        authenticationAdmin();
        User user = userRepository.findByLogin(principal.getName());

       if(carRepository.getByIdAndUser(id, user)==null)
           throw new IllegalAccessException("You can not delete not your's car, or your car isn't exists");


        Car car = carRepository.getById(id);
        if(carDetailRepository.getByCar_Id(id)!=null) {
            CarDetail carDetail = carDetailRepository.getByCar_Id(id);
            carDetailRepository.delete(carDetail);
        }
        carRepository.delete(car);
    }
}
