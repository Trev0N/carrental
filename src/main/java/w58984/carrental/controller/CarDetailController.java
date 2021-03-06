package w58984.carrental.controller;

import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import w58984.carrental.model.DTO.CarDetail.CarDetailCreateDTO;
import w58984.carrental.model.DTO.CarDetail.CarDetailDTO;
import w58984.carrental.model.DTO.CarDetail.CarDetailUpdateDTO;
import w58984.carrental.service.CarDetailService;
import w58984.carrental.service.CarService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Klasa z deklaracjami endpointów dla detali samochodów
 */
@RestController
@RequestMapping(value = "/cardetail")
public class CarDetailController {
    CarDetailService carDetailService;
    CarService carService;

    @Autowired
    public CarDetailController(CarDetailService carDetailService, CarService carService){this.carDetailService=carDetailService;
    this.carService=carService;}

    /**
     * <p>
     *     Metoda tworząca endpoint do wyświetlania wszystkich detali pojazdów dla określonego użytkownika
     * </p>
     * @param principal Informacje o użytkowniku pobierane automatycznie
     * @return Liste klasy CarDetailDTO oraz kod 200
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all my cars details", notes = "Get all my cars details.")
    public ResponseEntity<List<CarDetailDTO>> getAllMyCarsDetails(
            @ApiIgnore Principal principal){
        return ResponseEntity.status(HttpStatus.OK).body(carDetailService.getAllMyCarsDetails(principal));
    }

    /**
     * <p>
     *     Metoda tworząca endpoint do tworzenia nowych detali pojazdu
     * </p>
     * @param api Dane wymagane przy requescie do ustawienia nowych detali
     * @return kod 201
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "Add car details ", notes = "Add necessary car details to service. ")
    public ResponseEntity<Void> createCar(
            @RequestBody @Valid @NonNull final CarDetailCreateDTO api
    ){
        carService.authenticationAdmin();
        carDetailService.create(api);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    /**
     * <p>
     *     Metoda tworząca endpoint do zaktualizowania detali pojazdu
     * </p>
     * @param carDetailUpdateDTO Dane wymagane przy requescie do zaktualizowania detali pojazdu
     * @return kod 200
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value = "Update car detail",notes = "Update car details that you want. ")
    public ResponseEntity<CarDetailUpdateDTO> updateCarDetail(@RequestBody @Valid CarDetailUpdateDTO carDetailUpdateDTO){
        carService.authenticationAdmin();
       return ResponseEntity.status(HttpStatus.OK).body(carDetailService.updateDetails(carDetailUpdateDTO));
    }



}



