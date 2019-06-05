package w58984.carrental.controller;


import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import w58984.carrental.model.Api.car.CarEditApi;
import w58984.carrental.model.Api.car.CarCreateApi;
import w58984.carrental.model.DTO.Car.CarDTO;
import w58984.carrental.model.DTO.Car.CarReadyDTO;
import w58984.carrental.service.CarService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;

/**
 * Klasa z deklaracjami endpointów dla samochodów
 */
@RestController
@RequestMapping(value = "/car")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService){
        this.carService = carService;
    }

    /**
     * <p>Metoda tworząca endpoint do tworzenia pojazdu</p>
     * @param api Dane wymagane przy requescie, czyli dane nowego pojazdu
     * @param principal Informacje o użytkowniku pobierane automatycznie
     * @return Przy pomyślnym utworzeniu pojazdu kod 201
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "Create car ", notes = "Add car to this service. ")
    public ResponseEntity<Void> createCar(
            @RequestBody @Valid @NonNull final CarCreateApi api,
            @ApiIgnore
            Principal principal
            ){
        carService.authenticationAdmin();
        carService.create(api, principal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    /**
     * <p>
     *     Metoda tworząca endpoint do wyświetlania wszystkich pojazdów znajdujących się w serwisie
     * </p>
     * @return Liste klasy CarDTO oraz kod 200
     */
    @RequestMapping(value = "/public", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all", notes = "Get all cars.")
    public ResponseEntity<List<CarDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(carService.getAll());
    }

    /**
     * <p>
     *     Metoda tworząca endpoint do wyświetlania wszystkich pojazdów gotowych do wypożyczenia
     * </p>
     * @return Liste klasy CarReadyDTO oraz kod 200
     */
    @RequestMapping(path = "/readytorent",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all ready to rent", notes = "Get all ready to rent cars.")
    public ResponseEntity<List<CarReadyDTO>> getAllReady(){
        return ResponseEntity.status(HttpStatus.OK).body(carService.getAllReady());
    }


    /**
     * <p>
     *     Metoda tworząca endpoint do wyświetlania wszystkich pojazdów danego użytkowanika
     * </p>
     * @param principal Informacje o użytkowniku pobierane automatycznie
     * @return Liste klasy CarDTO oraz kod 200
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all my cars", notes = "Get all my cars.")
    public ResponseEntity<List<CarDTO>> getAllMyCars(
           @ApiIgnore Principal principal){
        return ResponseEntity.status(HttpStatus.OK).body(carService.getAllMyCars(principal));
    }


    /**
     * <p>
     *     Metoda tworząca endpoint do usuwania pojazdu z systemu
     * </p>
     * @param id ID pojazdu
     * @param principal Informacje o użytkowniku pobierane automatycznie
     * @return kod 204
     * @throws IllegalAccessException Jeśli nieodpowiednie uprawnienia zwraca wyjątek
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete car", notes = "Delete your car.")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") @NonNull final Long id,
                                       @ApiIgnore Principal principal) throws IllegalAccessException {


    carService.delete(id, principal);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * <p>
     *     Metoda tworząca endpoint do edytowania pojazdu w systemie
     * </p>
     * @param id ID pojazdu
     * @param api Dane wymagane przy requescie czyli dane po edycji pojazdu
     * @param principal Informacje o użytkowniku pobierane automatycznie
     * @return kod 201
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "Edit car ", notes = "Edit car in service. ")
    public ResponseEntity<Void> editCar(
            @PathVariable(value = "id") Long id,
            @RequestBody @Valid @NonNull final CarEditApi api,
            @ApiIgnore
                    Principal principal
    ){

        carService.update(id, api, principal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
