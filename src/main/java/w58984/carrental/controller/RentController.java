package w58984.carrental.controller;


import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import w58984.carrental.model.DTO.Car.CarRentDTO;
import w58984.carrental.model.DTO.Rent.RentCreateDTO;
import w58984.carrental.model.DTO.Rent.RentDTO;
import w58984.carrental.service.RentService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;

/**
 * Klasa z deklaracjami endpointów dla wypożyczeń pojazdów
 */
@RestController
@RequestMapping(value = "/rent")
public class RentController {
    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService){
        this.rentService=rentService;
    }

    /**
     * <p>
     *     Metoda tworząca endpoint do wyświetlania wszystkich twoich wypożyczeń
     * </p>
     * @param principal Informacje o użytkowniku pobierane automatycznie
     * @return Liste klasy RentDTO oraz kod 200
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get your rents ", notes = "Get your rent cars. ")
    public ResponseEntity<List<RentDTO>> getRent(
            @ApiIgnore
                    Principal principal
    ){
      return ResponseEntity.status(HttpStatus.OK).body(rentService.getAll(principal));
    }

    /**
     * <p>
     *     Metoda tworząca endpoint do utworzenia nowego wypożyczenia
     * </p>
     * @param id ID samochodu
     * @param api Dane wymagane w requescie do wypożyczenia pojazdu
     * @param principal Informacje o użytkowniku pobierane automatycznie
     * @return kod 201
     */
    @RequestMapping(value = "/car/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "Create rent ", notes = "Rent car. ")
    public ResponseEntity<Void> rentCar(
            @PathVariable(value = "id") final Long id,
            @RequestBody @Valid @NonNull final RentCreateDTO api,
            @ApiIgnore
                    Principal principal
    ){
       return rentService.findById(id).map(car -> {
           rentService.create(api, car.getId(), principal);
           return new ResponseEntity<Void>(HttpStatus.CREATED);
       }).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    /**
     * <p>
     *     Metoda tworząca endpoint do zwracania pojazdów
     * </p>
     * @param id ID wypożyczenia
     * @param principal Informacje o użytkowniku pobierane automatycznie
     * @return kod 204
     */
    @RequestMapping(value = "/return/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "End rent ", notes = "Return car. ")
    public ResponseEntity<Void> giveBack(
            @PathVariable(value = "id") @NonNull final Long id,
            @ApiIgnore
                    Principal principal
    ){
        rentService.delete(id, principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Metoda wyłapująca wyjątki
     * @return kod 403
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> exception(){
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


}
