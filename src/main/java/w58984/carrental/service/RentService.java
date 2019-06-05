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
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.OffsetDateTime.now;

/**
 * Klasa obsługująca endpointy dotyczące samochodów w serwisie
 */
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

    /**
     * <p>
     *     Klasa wyszukujaca wszystkie twoje wypozyczenia
     * </p>
     * @param principal Informacje o użytkowniku wywołującym metode
     * @return Liste wypożyczeń (klasa RentDTO)
     */
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


    /**
     * <p>
     *     Metoda tworząca nowe wypożyczenie w systemie
     * </p>
     * @param api Dane do utworzenia wypożyczenia
     * @param id ID pojazdu
     * @param principal Informacje o użytkowniku wywołującym metode
     */
    public void create(RentCreateDTO api, Long id, Principal principal){
        User user = userRepository.findByLogin(principal.getName());
        Car car =carRepository.findById(id).get();

        Preconditions.checkNotNull(carDetailRepository.getByCar_Id(id),"Car isn't ready to rent");


        if(carDetailRepository.getByCar_Id(id).getStatusEnum().equals(StatusEnum.READY_TO_RENT)&&api.getRentEndDate().isAfter(now())) {
            Rent rent = Rent.builder()
                    .car(car)
                    .user(user)
                    .rentEndDate(api.getRentEndDate())
                    .build();
            rentRepository.save(rent);
            CarDetail carDetail = carDetailRepository.getByCar_Id(id);
            carDetail.setStatusEnum(StatusEnum.RENTED);
            carDetailRepository.save(carDetail);
        } else
            throw new IllegalArgumentException("Car is not for rent, or you choose wrong date");

    }

    /**
     * <p>
     *     Metoda do zakończenia wypożyczenia w systemie
     * </p>
     * @param id ID Wypożyczenia
     * @param principal Informacje o użytkowniku wywołującym metode
     */
    public void delete(Long id, Principal principal){
        User user = userRepository.findByLogin(principal.getName());
        if(rentRepository.getByIdAndUser(id,user)==null)
            throw new IllegalArgumentException("Bad rent_id");
        Rent rent= rentRepository.getByIdAndUser(id, user);
        if(!rent.getRentEndDate().isBefore(OffsetDateTime.now())) {
            CarDetail carDetail = carDetailRepository.getByCar_Id(rent.getCar().getId());
            rent.setRentEndDate(now());
            carDetail.setStatusEnum(StatusEnum.NEED_ATTENTION);
            rentRepository.save(rent);
            carDetailRepository.save(carDetail);
        }
        else
            throw new IllegalArgumentException("You cannot rent finished renting");
    }



}
