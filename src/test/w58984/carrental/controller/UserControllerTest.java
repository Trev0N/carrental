package w58984.carrental.controller;

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
import w58984.carrental.model.DTO.User.UserCreateDTO;
import w58984.carrental.service.UserService;

import javax.transaction.Transactional;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        initMocks(this);
        UserController userController = new UserController(userService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        final UserCreateDTO api = UserCreateDTO.builder().login("test").mail("test@test.pl").password("testtest").firstName("a").lastName("a").recaptcha("aaa").build();

        String json = mapper.writeValueAsString(api);


        when(userService.findByLogin(api.getLogin())).thenReturn(null);


        mockMvc.perform(post("/user/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnBadRequestWhileCreateNewUser() throws Exception {
        final UserCreateDTO api = UserCreateDTO.builder().login("test").mail("test@test.pl").password("test").firstName("a").lastName("a").recaptcha("aaa").build();

        String json = mapper.writeValueAsString(api);


        when(userService.findByLogin(api.getLogin())).thenReturn(null);


        mockMvc.perform(post("/user/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

}
