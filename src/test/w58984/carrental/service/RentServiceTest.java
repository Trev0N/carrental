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
import w58984.carrental.model.DTO.Rent.RentCreateDTO;
import w58984.carrental.model.entity.*;
import w58984.carrental.model.entity.enums.StatusEnum;
import w58984.carrental.repository.CarDetailRepository;
import w58984.carrental.repository.CarRepository;
import w58984.carrental.repository.RentRepository;
import w58984.carrental.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class RentServiceTest {

    @InjectMocks
    private RentService rentService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RentRepository rentRepository;

    @Mock
    private CarDetailRepository carDetailRepository;

    @Before
    public void setUp(){
        initMocks(this);
        User user = new User(1L, User.Role.A, User.Status.A,"test","test","test@test.pl","test","test","test", OffsetDateTime.now(),OffsetDateTime.now());
        /* fill user object */


        List<GrantedAuthority> grantedAuths = Collections.singletonList(new SimpleGrantedAuthority("ROLEADMIN"));


        Authentication auth = new UsernamePasswordAuthenticationToken(user, null,grantedAuths);



        SecurityContextHolder.getContext().setAuthentication(auth);
        this.rentService = new RentService(userRepository,carRepository,rentRepository,carDetailRepository);
    }

    @Test
    public void shouldGetAllRents(){
        final Rent rent = Rent.builder()
                .rentStartDate(OffsetDateTime.now())
                .rentEndDate(OffsetDateTime.now())
                .car(Car.builder()
                .id(1L)
                .engine(2121).build())
                .id(1L)
                .build();

        final UserPrincipal principal = new UserPrincipal("test");
        final User user = new User();
        user.setLogin("test");
        user.setRole(User.Role.A);


        when(userRepository.findByLogin(principal.getName())).thenReturn(user);

        rentService.getAll(principal);

        verify(userRepository,times(1)).findByLogin(principal.getName());
        verify(rentRepository,times(1)).findAllByUser(user);
    }

    @Test
    public void shouldCreateRent(){
        final RentCreateDTO api = RentCreateDTO.builder()
                .rentEndDate(OffsetDateTime.now().plusHours(2L))
                .build();
        final Long id = 1L;
        final UserPrincipal principal = new UserPrincipal("test");
        final User user = new User();
        user.setLogin("test");
        user.setRole(User.Role.A);

        when(userRepository.findByLogin(principal.getName())).thenReturn(user);
        when(carRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(Car.builder()
                .id(1L)
                .user(user)
                .registerName("TEST")
                .modifiedAt(OffsetDateTime.now())
                .createdAt(OffsetDateTime.now())
                .power(1)
                .engine(1)
                .garage(Garage.builder()
                        .id(1L)
                        .address("aaa")
                        .name("aaa")
                        .build())
                .model("a")
                .mark("a")
                .build()));

        when(carDetailRepository.getByCar_Id(id)).thenReturn(CarDetail.builder()
        .statusEnum(StatusEnum.READY_TO_RENT)
        .build());
        rentService.create(api,id,principal);

        verify(userRepository,times(1)).findByLogin(principal.getName());
        verify(carDetailRepository,times(3)).getByCar_Id(id);

    }

    @Test
    public void shouldDeleteRent(){
        final Long id = 1L;
        final UserPrincipal principal = new UserPrincipal("test");
        final User user = new User();
        user.setLogin("test");
        user.setRole(User.Role.A);

        final Rent rent = Rent.builder()
                .car(Car.builder()
                        .id(1L)
                        .build())
                .id(1L)
                .rentEndDate(OffsetDateTime.now().plusDays(1))
                .rentStartDate(OffsetDateTime.now())
                .build();
        final CarDetail carDetail = CarDetail.builder()
                .statusEnum(StatusEnum.RENTED)
                .id(1L)
                .car(Car.builder().id(1L).build())
                .build();

        when(userRepository.findByLogin(principal.getName())).thenReturn(user);
        when(rentRepository.getByIdAndUser(id,user)).thenReturn(rent);

        when(carDetailRepository.getByCar_Id(1L)).thenReturn(carDetail);

        rentService.delete(id,principal);

        verify(rentRepository,times(2)).getByIdAndUser(id,user);
        verify(userRepository,times(1)).findByLogin(principal.getName());
        verify(carDetailRepository,times(1)).getByCar_Id(1L);
        verify(rentRepository,times(1)).save(rent);
        verify(carDetailRepository,times(1)).save(carDetail);
    }
}
