package w58984.carrental.controller;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import w58984.carrental.model.DTO.Car.CarCreateDTO;
import w58984.carrental.model.DTO.Car.CarDTO;
import w58984.carrental.model.entity.Garage;
import w58984.carrental.service.CarService;

import javax.validation.Valid;
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
        carService.create(api, principal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all", notes = "Get all cars.")
    public ResponseEntity<List<CarDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(carService.getAll());
    }


    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all my cars", notes = "Get all my cars.")
    public ResponseEntity<List<CarDTO>> getAllMyCars(Principal principal){
        return ResponseEntity.status(HttpStatus.OK).body(carService.getAllMyCars(principal));
    }


}
