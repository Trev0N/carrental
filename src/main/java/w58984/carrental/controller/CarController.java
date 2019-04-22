package w58984.carrental.controller;


import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import w58984.carrental.model.DTO.Car.CarCreateDTO;
import w58984.carrental.model.DTO.Car.CarDTO;
import w58984.carrental.model.DTO.Car.CarReadyDTO;
import w58984.carrental.service.CarService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/car")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService){
        this.carService = carService;
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "Create car ", notes = "Add car to this service. ")
    public ResponseEntity<Void> createCar(
            @RequestBody @Valid @NonNull final CarCreateDTO api,
            @ApiIgnore
            Principal principal
            ){
        carService.authenticationAdmin();
        carService.create(api, principal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }




    @RequestMapping(value = "/public", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all", notes = "Get all cars.")
    public ResponseEntity<List<CarDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(carService.getAll());
    }


    @RequestMapping(path = "/readytorent",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all ready to rent", notes = "Get all ready to rent cars.")
    public ResponseEntity<List<CarReadyDTO>> getAllReady(){
        return ResponseEntity.status(HttpStatus.OK).body(carService.getAllReady());
    }




    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all my cars", notes = "Get all my cars.")
    public ResponseEntity<List<CarDTO>> getAllMyCars(
           @ApiIgnore Principal principal){
        return ResponseEntity.status(HttpStatus.OK).body(carService.getAllMyCars(principal));
    }



    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete car", notes = "Delete your car.")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") @PathParam(value = "id") @NonNull final Long id,
                                       @ApiIgnore Principal principal) throws IllegalAccessException {
        carService.authenticationAdmin();

    carService.delete(id, principal);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
