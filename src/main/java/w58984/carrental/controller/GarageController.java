package w58984.carrental.controller;


import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import w58984.carrental.model.Api.garage.EditGarageApi;
import w58984.carrental.model.Api.garage.GarageCreateApi;
import w58984.carrental.model.DTO.Garage.GarageDTO;
import w58984.carrental.service.GarageService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Klasa z deklaracjami endpointów dla garaży
 */
@RestController
@RequestMapping(value = "/garage")
public class GarageController {
    private GarageService garageService;

    @Autowired
    public GarageController(GarageService garageService){
        this.garageService=garageService;
    }

    /**
     * <p>
     *     Metoda tworząca endpoint do wyświetlania list wszystkich garaży
     * </p>
     * @return Liste klasy GarageDTO oraz kod 200
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get garages ", notes = "Get garage list in this service. ")
    public ResponseEntity<List<GarageDTO>> getGarage(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(garageService.getGarages());
    }


    /**
     * <p>
     *     Metoda tworząca endpoint do tworzenia nowego garażu
     * </p>
     * @param api Dane wymagane przy requescie do utworzenia nowego garażu
     * @return kod 201
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "Create garage ", notes = "Add garage to this service. ")
    public ResponseEntity<Void> createGarage(
            @RequestBody @Valid @NonNull final GarageCreateApi api
    ){
        garageService.create(api);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * <p>
     *     Metoda tworząca endpoint do edycji garażu
     * </p>
     * @param id ID garażu
     * @param api Dane wymagane przy requescie do edycji garażu
     * @return kod 200
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Edit garage ", notes = "Edit garage in this service. ")
    public ResponseEntity<Void> editGarage(
            @PathVariable(value = "id") final Long id,
            @RequestBody @Valid @NonNull final EditGarageApi api
    ){
        return garageService.findById(id).map(
                garage -> {
                    garageService.edit(garage.getId(),api);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));

    }

    /**
     * <p>
     *     Metoda tworząca endpoint do usuwania garażu
     * </p>
     * @param id ID garażu
     * @return kod 204
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete garage ", notes = "Delete garage in this service. ")
    public ResponseEntity<Void> deleteGarage(
            @PathVariable(value = "id") final Long id
    ){

        return garageService.findById(id).map(
                garage -> {
                    garageService.delete(garage.getId());
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                }
        ).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }


}
