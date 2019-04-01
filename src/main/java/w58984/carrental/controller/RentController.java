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
import springfox.documentation.annotations.ApiIgnore;
import w58984.carrental.model.DTO.Car.CarRentDTO;
import w58984.carrental.model.DTO.Rent.RentCreateDTO;
import w58984.carrental.service.RentService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/rent")
public class RentController {
    private final RentService rentService;

    @Autowired
    private RentController(RentService rentService){
        this.rentService=rentService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "Create rent ", notes = "Rent car. ")
    public ResponseEntity<Void> rentCar(
            @RequestBody @Valid @NonNull final RentCreateDTO api,@RequestBody CarRentDTO apicar,
            @ApiIgnore
                    Principal principal
    ){
        rentService.create(api, apicar, principal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
