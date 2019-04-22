package w58984.carrental.controller;


import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import w58984.carrental.model.Api.garage.EditGarageApi;
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

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Edit garage ", notes = "Edit garage in this service. ")
    public ResponseEntity<Void> editGarage(
            @PathVariable(value = "id") final Long id,
            @RequestBody @Valid @NonNull final EditGarageApi api
    ){

        garageService.edit(id,api);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete garage ", notes = "Delete garage in this service. ")
    public ResponseEntity<Void> deleteGarage(
            @PathVariable(value = "id") final Long id
    ){

        garageService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
