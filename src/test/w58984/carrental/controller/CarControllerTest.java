package w58984.carrental.controller;

import com.sun.security.auth.UserPrincipal;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import w58984.carrental.model.Api.car.CarCreateApi;
import w58984.carrental.model.Api.car.CarEditApi;
import w58984.carrental.model.DTO.Car.CarDTO;
import w58984.carrental.model.entity.Car;
import w58984.carrental.service.CarService;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class CarControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private CarService carService;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        initMocks(this);
        CarController carController = new CarController(carService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    public void shouldCreateNewCar() throws Exception {
        final CarCreateApi api = CarCreateApi.builder()
                .mark("Skoda")
                .model("Superb")
                .garageId(1L)
                .registerName("RZ 22223")
                .build();

        final UserPrincipal principal = new UserPrincipal("test");

        String json = mapper.writeValueAsString(api);

        mockMvc.perform(post("/car/create").principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }
    @Test
    public void shouldReturnBadRequestWhileCreateNewCar() throws Exception {
        final CarCreateApi api= CarCreateApi.builder()
                .mark(null)
                .model(null)
                .registerName("RZ 21213")
                .build();

        final UserPrincipal principal = new UserPrincipal("test");

        String json = mapper.writeValueAsString(api);

        mockMvc.perform(post("/car/create").principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFindAllUserCars() throws Exception {
        final UserPrincipal principal = new UserPrincipal("test");

        when(carService.getAllMyCars(principal)).thenReturn(Collections.singletonList(new CarDTO()));

        mockMvc.perform(get("/car/").principal(principal))
                .andExpect(status().isOk());
    }


    @Test
    public void shouldUpdateCar() throws Exception {

        final Car car = Car.builder().id(1L).build();

        final UserPrincipal principal = new UserPrincipal("test");


        when(carService.findById(car.getId())).thenReturn(Optional.of(car));

        CarEditApi api = CarEditApi.builder()
                .mark("Opel")
                .model("Corsa")
                .garageId(2L)
                .registerName("RZ 0000")
                .build();

        String json = mapper.writeValueAsString(api);

        mockMvc.perform(put("/car/edit/{id}", car.getId())
                .principal(principal)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    @Test
    public void shouldReturnNotFoundWhileUpdateCar() throws Exception {

        final Car car = Car.builder().id(1L).build();

        final UserPrincipal principal = new UserPrincipal("test");

        when(carService.findById(car.getId())).thenReturn(Optional.empty());

        CarEditApi api = CarEditApi.builder()
                .mark("Opel")
                .model("Corsa")
                .garageId(1L)
                .registerName("RZ 21212")
                .build();

        String json = mapper.writeValueAsString(api);

        mockMvc.perform(put("/car/edit/{id}", car.getId())
                .principal(principal)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldDeleteCar() throws Exception {

        final Car car = Car.builder().id(1L).build();

        when(carService.findById(car.getId())).thenReturn(Optional.of(car));

        mockMvc.perform(delete("/car/delete/{id}", car.getId()))
                .andExpect(status().isNoContent());
    }


    @Test
    public void shouldReturnNotFoundWhileDeleteCar() throws Exception {

    final Car car = Car.builder().id(1L).build();
        when(carService.findById(car.getId())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/car/delete/{id}", car.getId()))
                .andExpect(status().isNotFound());
    }

}
