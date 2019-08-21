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
import w58984.carrental.model.DTO.Rent.RentCreateDTO;
import w58984.carrental.model.entity.Car;
import w58984.carrental.model.entity.CarDetail;
import w58984.carrental.model.entity.Rent;
import w58984.carrental.model.entity.User;
import w58984.carrental.model.entity.enums.StatusEnum;
import w58984.carrental.service.CarService;
import w58984.carrental.service.RentService;

import javax.transaction.Transactional;


import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class RentControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private RentService rentService;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        initMocks(this);
        RentController rentController = new RentController(rentService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(rentController).build();
    }

    @Test
    public void shouldFindAllRents() throws Exception {
        UserPrincipal principal = new UserPrincipal("test");

        User user = User.builder()
                .id(1L)
                .build();

        when(rentService.findAllByUser(user)).thenReturn(Collections.singletonList(Rent.builder()
                .id(1L).build()));


        mockMvc.perform(get("/rent").principal(principal))
                .andExpect(status().isOk());

    }
    @Test
    public void shouldDeleteRent() throws Exception {

        final Car car = Car.builder().id(1L).build();

        final UserPrincipal principal = new UserPrincipal("test");


        mockMvc.perform(delete("/rent/return/{id}", car.getId())
        .principal(principal))
                .andExpect(status().isNoContent());
    }




}
