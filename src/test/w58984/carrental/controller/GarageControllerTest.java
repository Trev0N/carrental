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
import w58984.carrental.model.Api.garage.EditGarageApi;
import w58984.carrental.model.Api.garage.GarageCreateApi;
import w58984.carrental.model.DTO.CarDetail.CarDetailCreateDTO;
import w58984.carrental.model.DTO.Garage.GarageDTO;
import w58984.carrental.model.entity.CarDetail;
import w58984.carrental.model.entity.Garage;
import w58984.carrental.model.entity.User;
import w58984.carrental.model.entity.enums.StatusEnum;
import w58984.carrental.service.CarDetailService;
import w58984.carrental.service.CarService;
import w58984.carrental.service.GarageService;

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
public class GarageControllerTest {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private GarageService garageService;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        initMocks(this);
        GarageController garageController = new GarageController(garageService);
        this.mockMvc= MockMvcBuilders.standaloneSetup(garageController).build();
    }

    @Test
    public void shouldUpdateGarage() throws Exception {

        final Garage garage = Garage.builder().id(1L).build();

        final UserPrincipal principal = new UserPrincipal("test");


        when(garageService.findById(garage.getId())).thenReturn(Optional.of(garage));

        EditGarageApi api = EditGarageApi.builder()
                .name("aaatesty")
                .address("aaaa")
                .build();

        String json = mapper.writeValueAsString(api);

        mockMvc.perform(put("/garage/edit/{id}",garage.getId())
                .principal(principal)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhileUpdateGarage() throws Exception {

        final Garage garage = Garage.builder().id(1L).build();

        final UserPrincipal principal = new UserPrincipal("test");


        when(garageService.findById(garage.getId())).thenReturn(Optional.empty());

        EditGarageApi api = EditGarageApi.builder()
                .name("aaatesty")
                .address("aaaa")
                .build();

        String json = mapper.writeValueAsString(api);

        mockMvc.perform(put("/garage/edit/{id}",garage.getId())
                .principal(principal)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateNewGarage() throws Exception {
        final GarageCreateApi api = GarageCreateApi.builder()
                .name("test")
                .address("test")
                .build();

        final UserPrincipal principal = new UserPrincipal("test");

        String json = mapper.writeValueAsString(api);

        mockMvc.perform(post("/garage/create").principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }


    @Test
    public void shouldFindAllGarages() throws Exception {
        final UserPrincipal principal = new UserPrincipal("test");

        when(garageService.getGarages()).thenReturn(Collections.singletonList(new GarageDTO()));

        mockMvc.perform(get("/garage").principal(principal))
                .andExpect(status().isOk());
    }

@Test
    public void shouldDeleteGarage() throws Exception {
        final UserPrincipal principal = new UserPrincipal("test");

        final Garage garage = Garage.builder()
                .id(1L)
                .build();

        when(garageService.findById(garage.getId())).thenReturn(Optional.of(garage));

        mockMvc.perform(delete("/garage/delete/{id}", garage.getId()))
                .andExpect(status().isNoContent());

    }

    @Test
    public void shouldReturnNotFoundWhileDeleteGarage() throws Exception {
        final UserPrincipal principal = new UserPrincipal("test");

        final Garage garage = Garage.builder()
                .id(1L)
                .build();

        when(garageService.findById(garage.getId())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/garage/delete/{id}", garage.getId()))
                .andExpect(status().isNotFound());

    }
}
