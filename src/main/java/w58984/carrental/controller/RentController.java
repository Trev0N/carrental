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

@RestController
@RequestMapping(value = "/rent")
public class RentController {
    private final RentService rentService;

    @Autowired
    private RentController(RentService rentService){
        this.rentService=rentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get your rents ", notes = "Get your rent cars. ")
    public ResponseEntity<List<RentDTO>> getRent(
            @ApiIgnore
                    Principal principal
    ){
      return ResponseEntity.status(HttpStatus.OK).body(rentService.getAll(principal));
    }


    @RequestMapping(value = "/car/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "Create rent ", notes = "Rent car. ")
    public ResponseEntity<Void> rentCar(
            @PathVariable(value = "id") final Long id,
            @RequestBody @Valid @NonNull final RentCreateDTO api,
            @ApiIgnore
                    Principal principal
    ){
        rentService.create(api, id, principal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/return/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "End rent ", notes = "Return car. ")
    public ResponseEntity<Void> giveBack(
            @PathVariable(value = "id") @PathParam(value = "id") @NonNull final Long id,
            @ApiIgnore
                    Principal principal
    ){
        rentService.delete(id, principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
