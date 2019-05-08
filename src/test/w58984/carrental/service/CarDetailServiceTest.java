package w58984.carrental.service;

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
import w58984.carrental.model.DTO.CarDetail.CarDetailCreateDTO;
import w58984.carrental.model.DTO.CarDetail.CarDetailUpdateDTO;
import w58984.carrental.model.entity.Car;
import w58984.carrental.model.entity.CarDetail;
import w58984.carrental.model.entity.Garage;
import w58984.carrental.model.entity.User;
import w58984.carrental.model.entity.enums.StatusEnum;
import w58984.carrental.repository.CarDetailRepository;
import w58984.carrental.repository.CarRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class CarDetailServiceTest {

    @InjectMocks
    private CarDetailService carDetailService;

    @Mock
    private CarRepository carRepository;

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
        this.carDetailService = new CarDetailService(carRepository,carDetailRepository);
    }
    @Test
    public void shouldCreateCarDetail(){
        final CarDetailCreateDTO api = CarDetailCreateDTO.builder()
                .carId(1L)
                .mileage(1L)
                .price(new BigDecimal(1))
                .statusEnum(StatusEnum.READY_TO_RENT)
                .build();

            when(carRepository.findById(api.getCarId())).thenReturn(java.util.Optional.ofNullable(new Car().toBuilder()
                    .id(1L)
                    .engine(1)
                    .mark("test")
                    .model("test")
                    .power(1)
                    .registerName("test")
                    .createdAt(OffsetDateTime.now())
                    .modifiedAt(OffsetDateTime.now())
                    .garage(new Garage().toBuilder()
                            .address("test")
                            .name("test")
                            .id(1L)
                            .build())
                    .build()));
            when(carDetailRepository.getByCar_Id(api.getCarId())).thenReturn(null);
        carDetailService.create(api);

        verify(carRepository,times(1)).getById(api.getCarId());
        verify(carDetailRepository,times(1)).getByCar_Id(api.getCarId());
        verify(carDetailRepository,times(1)).save(any(CarDetail.class));

        verifyNoMoreInteractions(carRepository);
        verifyNoMoreInteractions(carDetailRepository);

    }
    @Test
    public void shouldUpdateCarDetail(){
        final CarDetailUpdateDTO api = CarDetailUpdateDTO.builder()
                .carId(1L)
                .mileage(100L)
                .price(new BigDecimal(100))
                .statusEnum(StatusEnum.READY_TO_RENT)
                .build();

        when(carDetailRepository.getByCar_Id(api.getCarId())).thenReturn(new CarDetail().toBuilder()
        .car(new Car().toBuilder()
                .id(1L)
                .engine(1)
                .mark("test")
                .model("test")
                .power(1)
                .registerName("test")
                .createdAt(OffsetDateTime.now())
                .modifiedAt(OffsetDateTime.now())
                .garage(new Garage().toBuilder()
                        .address("test")
                        .name("test")
                        .id(1L)
                        .build())
                .build())
        .mileage(1L)
        .price(new BigDecimal(1))
        .statusEnum(StatusEnum.NEED_ATTENTION)
        .build());
        when(carRepository.getById(api.getCarId())).thenReturn(new Car().toBuilder()
                .id(1L)
                .engine(1)
                .mark("test")
                .model("test")
                .power(1)
                .registerName("test")
                .createdAt(OffsetDateTime.now())
                .modifiedAt(OffsetDateTime.now())
                .garage(new Garage().toBuilder()
                        .address("test")
                        .name("test")
                        .id(1L)
                        .build())
                .build());




        carDetailService.updateDetails(api);

        verify(carDetailRepository,times(2)).getByCar_Id(api.getCarId());
        verify(carRepository,times(1)).getById(api.getCarId());
        verify(carDetailRepository,times(1)).save(any(CarDetail.class));
        verifyNoMoreInteractions(carDetailRepository);
        verifyNoMoreInteractions(carRepository);

    }




}
