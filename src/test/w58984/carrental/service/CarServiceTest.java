package w58984.carrental.service;


import com.sun.security.auth.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import w58984.carrental.model.entity.Car;
import w58984.carrental.model.entity.Garage;
import w58984.carrental.model.entity.User;
import w58984.carrental.repository.CarDetailRepository;
import w58984.carrental.repository.CarRepository;
import w58984.carrental.repository.GarageRepository;
import w58984.carrental.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarDetailRepository carDetailRepository;

    @Mock
    private GarageRepository garageRepository;

    @Before
    public void setUp(){
        initMocks(this);
        User user = new User(1L, User.Role.A, User.Status.A,"test","test","test@test.pl","test","test","test",OffsetDateTime.now(),OffsetDateTime.now());
        /* fill user object */


        List<GrantedAuthority> grantedAuths = Collections.singletonList(new SimpleGrantedAuthority("ROLEADMIN"));


        Authentication auth = new UsernamePasswordAuthenticationToken(user, null,grantedAuths);



        SecurityContextHolder.getContext().setAuthentication(auth);
        this.carService = new CarService(userRepository,carRepository,carDetailRepository, garageRepository);
    }

    @Test
    public void shouldFindById() {
        final Car car = Car.builder().id(1L).build();

        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));

        carService.findById(1L);

        verify(carRepository, times(1)).findById(car.getId());
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    public void shouldGetAllCars() {
        final Car car = Car.builder()
                .id(1L)
                .garage(Garage.builder()
                        .id(1L).build())
                .engine(1)
                .mark("test")
                .model("test")
                .power(1)
                .registerName("test")
                .user(User.builder()
                .id(1L)
                .build())
                .createdAt(OffsetDateTime.now())
                .modifiedAt(OffsetDateTime.now())
                .build();

        when(carRepository.findAll()).thenReturn(Collections.singletonList(car));

        carService.getAll();

        verify(carRepository,times(1)).findAll();
        verifyNoMoreInteractions(carRepository);
    }
    @Test
    public void shouldGetAllMyCars() {
        final Car car = Car.builder()
                .id(1L)
                .garage(Garage.builder()
                        .id(1L).build())
                .engine(1)
                .mark("test")
                .model("test")
                .power(1)
                .registerName("test")
                .user(User.builder()
                        .id(1L)
                        .login("test")
                        .build())
                .createdAt(OffsetDateTime.now())
                .modifiedAt(OffsetDateTime.now())
                .build();

        final UserPrincipal principal = new UserPrincipal("test");
        final User user = new User();
        user.setLogin("test");
        user.setRole(User.Role.A);


        when(userRepository.findByLogin(principal.getName())).thenReturn(user);


        carService.getAllMyCars(principal);

        verify(userRepository, times(1)).findByLogin(user.getLogin());
        verify(carRepository, times(1)).findByUser(user);

        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(carRepository);

    }

}
