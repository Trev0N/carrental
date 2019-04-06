package w58984.carrental.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import w58984.carrental.model.DTO.User.UserCreateDTO;
import w58984.carrental.service.UserService;

import javax.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    @ApiOperation(value = "Sign in ", notes = "Sign in to this service. ")
    public ResponseEntity<Void> signIn(
            @RequestBody @Valid UserCreateDTO api
    ) {
        userService.create(api);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
