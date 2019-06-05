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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

/**
 * Klasa z deklaracjami endpointów dla użytkowników
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * <p>
     *     Metoda tworząca endpoint do utworzenia nowego konta
     * </p>
     * @param api Dane wymagane przy requescie do utworzenia konta
     * @return kod 201
     */
    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    @ApiOperation(value = "Sign in ", notes = "Sign in to this service. ")
    public ResponseEntity<Void> signIn(
            @RequestBody @Valid UserCreateDTO api
    ) {
        userService.create(api);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * <p>
     *     Metoda tworząca endpoint do wylogowywania z serwisu
     * </p>
     * @param request Dane requesta pobierane automatycznie
     * @return Prawda jeśli wylogowano oraz kod 200
     */
    @RequestMapping(value = "/sign-out", method = RequestMethod.DELETE)
    @ApiOperation(value = "Sign out ", notes = "Sign out from this service. ")
    public ResponseEntity<Boolean> singOut(HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.deleteToken(request));
    }

    /**
     * <p>
     *     Metoda tworząca endpoint do sprawdzenia czy użytkownik jest adminem
     * </p>
     * @param principal Informacje o użytkowniku pobierane automatycznie
     * @return prawda jeśli jest adminem oraz kod 200
     */
    @RequestMapping(value = "/isadmin", method = RequestMethod.POST)
    @ApiOperation(value = "Get role ", notes = "Get role to this service. ")
    public ResponseEntity<Boolean> getRole(Principal principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.isAdmin(principal));

    }
}
