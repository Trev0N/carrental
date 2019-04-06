package w58984.carrental.controller;

import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import w58984.carrental.model.DTO.Car.CarCreateDTO;
import w58984.carrental.model.DTO.CarDetail.CarDetailCreateDTO;
import w58984.carrental.model.entity.CarDetail;
import w58984.carrental.service.CarDetailService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/cardetail")
public class CarDetailController {
    CarDetailService carDetailService;

    @Autowired
    public CarDetailController(CarDetailService carDetailService){this.carDetailService=carDetailService;}

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "Add car details ", notes = "Add necessary car details to service. ")
    public ResponseEntity<Void> createCar(
            @RequestBody @Valid @NonNull final CarDetailCreateDTO api
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLEADMIN"));
        if (!hasUserRole) {
            throw new IllegalArgumentException("You don't have permission");
        }
        carDetailService.create(api);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
