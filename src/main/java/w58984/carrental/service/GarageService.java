package w58984.carrental.service;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w58984.carrental.model.Api.garage.EditGarageApi;
import w58984.carrental.model.DTO.Garage.GarageCreateDTO;
import w58984.carrental.model.entity.Car;
import w58984.carrental.model.entity.Garage;
import w58984.carrental.model.entity.User;
import w58984.carrental.repository.GarageRepository;
import w58984.carrental.repository.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GarageService {
    private GarageRepository garageRepository;
    private CarService carService;

    @Autowired
    public GarageService(GarageRepository garageRepository, CarService carService) {
        this.garageRepository=garageRepository;
        this.carService=carService;
    }



    public void create(GarageCreateDTO api){
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

    public void edit(Long id ,EditGarageApi api){

        carService.authenticationAdmin();


        Preconditions.checkNotNull(garageRepository.findById(id),"Wrong garage id");

        Optional.ofNullable(garageRepository.findByName(api.getName())).ifPresent(
                e-> {
                    throw new IllegalArgumentException("Garage with the same name exists");
                }
        );


        Garage garage = garageRepository.findById(id).get();


        garageRepository.save(garage.toBuilder()
                .address(api.getAddress())
                .name(api.getName())
                .build());

    }


    public void delete(Long id){
        carService.authenticationAdmin();

        Preconditions.checkNotNull(garageRepository.findById(id),"Wrong garage id");
        Garage garage = garageRepository.findById(id).get();

        garageRepository.delete(garage);

    }



}
