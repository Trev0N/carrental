package w58984.carrental.service;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w58984.carrental.model.Api.garage.EditGarageApi;
import w58984.carrental.model.Api.garage.GarageCreateApi;
import w58984.carrental.model.DTO.Garage.GarageDTO;
import w58984.carrental.model.entity.Garage;
import w58984.carrental.repository.GarageRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Klasa obsługująca endpointy dotyczące garaży w serwisie
 */
@Service
public class GarageService {
    private GarageRepository garageRepository;
    private CarService carService;

    @Autowired
    public GarageService(GarageRepository garageRepository, CarService carService) {
        this.garageRepository=garageRepository;
        this.carService=carService;
    }

    /**
     * <p>
     *     Metoda szukająca wszystkie garaże w systemie
     * </p>
     * @return Liste garaży (klasa GarageDTO)
     */
    public List<GarageDTO> getGarages(){
        carService.authenticationAdmin();


       return garageRepository.findAll().stream().map(
          row -> GarageDTO.builder()
                  .id(row.getId())
                  .address(row.getAddress())
                  .name(row.getName())
                  .build()).collect(Collectors.toList());
    }

    /**
     * <p>
     *     Metoda tworząca nowy garaż w systemie
     * </p>
     * @param api Dane do utworzenia nowego garażu
     */
    public void create(GarageCreateApi api){
        carService.authenticationAdmin();
        Optional.ofNullable(garageRepository.findByName(api.getName())).ifPresent(
                e-> {
                    throw new IllegalArgumentException("Garage with the same name exists");
                }
        );



        Garage garage = Garage.builder()
                .name(api.getName())
                .address(api.getAddress())
                .build();
        garageRepository.save(garage);
    }

    /**
     * <p>
     *     Metoda edytujca garaże w systemie
     * </p>
     * @param id ID garażu
     * @param api Dame do zaktualizowania garażu
     */
    public void edit(Long id ,EditGarageApi api){

        carService.authenticationAdmin();


        Preconditions.checkNotNull(garageRepository.findById(id),"Wrong garage id");

        Optional.ofNullable(garageRepository.findByName(api.getName())).ifPresent(
                e->
                {
                    if(e.getId()!=id)
                    throw new IllegalArgumentException("Garage with the same name exists");
                }
        );


        Garage garage = garageRepository.findById(id).get();


        garageRepository.save(garage.toBuilder()
                .address(api.getAddress())
                .name(api.getName())
                .build());

    }

    /**
     * <p>
     *     Metoda do usunięcia garażu
     * </p>
     * @param id ID garażu
     */
    public void delete(Long id){
        carService.authenticationAdmin();

        Preconditions.checkNotNull(garageRepository.findById(id),"Wrong garage id");
        Garage garage = garageRepository.findById(id).get();

        garageRepository.delete(garage);

    }



}
