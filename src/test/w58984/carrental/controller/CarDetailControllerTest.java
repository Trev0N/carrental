package w58984.carrental.controller;

import com.sun.security.auth.UserPrincipal;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import w58984.carrental.model.Api.car.CarCreateApi;
import w58984.carrental.model.Api.car.CarEditApi;
import w58984.carrental.model.DTO.Car.CarDTO;
import w58984.carrental.model.DTO.CarDetail.CarDetailCreateDTO;
import w58984.carrental.model.entity.Car;
import w58984.carrental.model.entity.CarDetail;
import w58984.carrental.model.entity.User;
import w58984.carrental.model.entity.enums.StatusEnum;
import w58984.carrental.service.CarDetailService;
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
public class CarDetailControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private CarDetailService carDetailService;

    @Mock
    private CarService carService;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        initMocks(this);
        CarDetailController carDetailController = new CarDetailController(carDetailService, carService);
        this.mockMvc= MockMvcBuilders.standaloneSetup(carDetailController).build();
    }


    @Test
    public void shouldUpdateCarDetail() throws Exception {

        final CarDetail carDetail = CarDetail.builder().id(1L).build();

        final UserPrincipal principal = new UserPrincipal("test");


        when(carDetailService.findById(carDetail.getId())).thenReturn(Optional.of(carDetail));

        CarDetailCreateDTO carDetailCreateDTO = CarDetailCreateDTO.builder()
                .carId(1L)
                .mileage(100L)
                .statusEnum(StatusEnum.READY_TO_RENT)
                .build();


        String json = mapper.writeValueAsString(carDetailCreateDTO);

        mockMvc.perform(put("/cardetail/update")
                .principal(principal)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhileUpdateCarDetail() throws Exception {

        final CarDetail carDetail = CarDetail.builder().id(1L).build();

        final UserPrincipal principal = new UserPrincipal("test");


        when(carService.findById(carDetail.getId())).thenReturn(Optional.empty());

        CarDetailCreateDTO carDetailCreateDTO = CarDetailCreateDTO.builder()
                .carId(1L)
                .mileage(100L)
                .statusEnum(StatusEnum.READY_TO_RENT)
                .build();


        String json = mapper.writeValueAsString(carDetailCreateDTO);

        mockMvc.perform(put("/cardetail/update")
                .principal(principal)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateNewCarDetail() throws Exception {
        final CarDetailCreateDTO api = CarDetailCreateDTO.builder()
                .carId(1L)
                .mileage(199L)
                .build();

        final UserPrincipal principal = new UserPrincipal("test");

        String json = mapper.writeValueAsString(api);

        mockMvc.perform(post("/cardetail/create").principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }


    @Test
    public void shouldFindAllUserCarDetails() throws Exception {
        final UserPrincipal principal = new UserPrincipal("test");

        when(carDetailService.findAllByCarUser(User.builder().id(1L).role(User.Role.A).build())).thenReturn(Collections.singletonList(new CarDetail()));

        mockMvc.perform(get("/cardetail/").principal(principal))
                .andExpect(status().isOk());
    }
}
