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
import w58984.carrental.model.DTO.Garage.GarageCreateDTO;
import w58984.carrental.service.GarageService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/garage")
public class GarageController {
    private GarageService garageService;

    @Autowired
    public GarageController(GarageService garageService){
        this.garageService=garageService;
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "Create garage ", notes = "Add garage to this service. ")
    public ResponseEntity<Void> createGarage(
            @RequestBody @Valid @NonNull final GarageCreateDTO api
    ){
        garageService.create(api);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
