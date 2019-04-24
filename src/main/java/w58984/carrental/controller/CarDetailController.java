package w58984.carrental.controller;

import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import w58984.carrental.model.DTO.CarDetail.CarDetailCreateDTO;
import w58984.carrental.model.DTO.CarDetail.CarDetailUpdateDTO;
import w58984.carrental.service.CarDetailService;
import w58984.carrental.service.CarService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/cardetail")
public class CarDetailController {
    CarDetailService carDetailService;
    CarService carService;

    @Autowired
    public CarDetailController(CarDetailService carDetailService, CarService carService){this.carDetailService=carDetailService;
    this.carService=carService;}

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "Add car details ", notes = "Add necessary car details to service. ")
    public ResponseEntity<Void> createCar(
            @RequestBody @Valid @NonNull final CarDetailCreateDTO api
    ){
        carService.authenticationAdmin();
        carDetailService.create(api);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value = "Update car detail",notes = "Update car details that you want. ")
    public ResponseEntity<CarDetailUpdateDTO> updateCarDetail(@RequestBody CarDetailUpdateDTO carDetailUpdateDTO){
        carService.authenticationAdmin();
       return ResponseEntity.status(HttpStatus.OK).body(carDetailService.updateDetails(carDetailUpdateDTO));
    }



}



