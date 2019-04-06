package w58984.carrental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    public GarageService(GarageRepository garageRepository) {
        this.garageRepository=garageRepository;
    }

    public void create(GarageCreateDTO api){
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

}
