package w58984.carrental.service;

import com.sun.security.auth.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import w58984.carrental.model.Api.garage.EditGarageApi;
import w58984.carrental.model.Api.garage.GarageCreateApi;
import w58984.carrental.model.entity.Garage;
import w58984.carrental.model.entity.User;
import w58984.carrental.repository.GarageRepository;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class GarageServiceTest {

    @InjectMocks
    private GarageService garageService;

    @Mock
    private CarService carService;


    @Mock
    private GarageRepository garageRepository;


    @Before
    public void setUp(){
        initMocks(this);
        User user = new User(1L, User.Role.A, User.Status.A,"test","test","test@test.pl","test","test","test", OffsetDateTime.now(),OffsetDateTime.now());
        /* fill user object */


        List<GrantedAuthority> grantedAuths = Collections.singletonList(new SimpleGrantedAuthority("ROLEADMIN"));


        Authentication auth = new UsernamePasswordAuthenticationToken(user, null,grantedAuths);



        SecurityContextHolder.getContext().setAuthentication(auth);
        this.garageService = new GarageService(garageRepository,carService);
    }

    @Test
    public void shouldCreateGarage(){
        final GarageCreateApi api = GarageCreateApi.builder()
                .name("test")
                .address("test")
                .build();

        final UserPrincipal principal = new UserPrincipal("test");
        final User user = new User();
        user.setLogin("test");

        garageService.create(api);

        verify(garageRepository, times(1)).findByName(api.getName());
        verify(garageRepository, times(1)).save(any(Garage.class));
        verifyNoMoreInteractions(garageRepository);
    }
    @Test
    public void shouldEditGarage(){
        EditGarageApi api = EditGarageApi.builder()
                .name("test")
                .address("test")
                .build();
        final UserPrincipal principal = new UserPrincipal("test");
        final User user = new User();
        user.setLogin("test");
        final Long id = 1L;

        when(garageRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(new Garage().toBuilder().id(1L).address("test").name("test").build()));

        garageService.edit(id,api);

        verify(garageRepository,times(2)).findById(id);
        verify(garageRepository,times(1)).findByName(api.getName());
        verify(garageRepository,times(1)).save(any(Garage.class));
        verifyNoMoreInteractions(garageRepository);
    }
    @Test
    public void shouldDeleteGarage(){

        Garage garage = new Garage().toBuilder().id(1L).address("test").name("test").build();
        when(garageRepository.findById(garage.getId())).thenReturn(java.util.Optional.ofNullable(garage));

        garageService.delete(garage.getId());

        verify(garageRepository,times(2)).findById(garage.getId());
        verify(garageRepository,times(1)).delete(any(Garage.class));
        verifyNoMoreInteractions(garageRepository);


    }


}
