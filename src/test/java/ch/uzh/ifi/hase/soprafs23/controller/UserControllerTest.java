
package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.Application;
import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPutDTO;
import ch.uzh.ifi.hase.soprafs23.service.GameService;
import ch.uzh.ifi.hase.soprafs23.service.ShipPlayerService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;
import ch.uzh.ifi.hase.soprafs23.exceptions.UserExcep;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private User user;

    @MockBean
    private ShipPlayerService shipPlayerService;

    @MockBean
    private GameService gameService;

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;


    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
        // given
        User user = new User();
        System.out.println(user);
        user.setUsername("Mario");
        user.setId(1L);
        user.setPassword("Admin");
        user.setTotalWins(5);

        List<User> allUsers = Collections.singletonList(user);

        // this mocks the UserService -> we define above what the userService should
        // return when getUsers() is called
        given(userService.getUsers()).willReturn(allUsers);

        // when
        MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())))
                .andExpect(jsonPath("$[0].totalWins", is(user.getTotalWins())));


    }

    //F1 why is conflict / notfound
    @Test
    public void givenNoUsers_whenGetUsers_ThrowExcep() throws Exception {

        given(userService.getUsers()).willThrow(new UserExcep("no users found"));

        // when
        MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isConflict());

    }

    @Test
    public void createUser_validInput_userCreated() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setPassword("Test Password");
        user.setUsername("testUsername");
        user.setToken("1");
        user.setStatus(UserStatus.ONLINE);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("Test Password");
        userPostDTO.setUsername("testUsername");

        given(userService.createUser(any())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())));


    }

    @Test
    public void createUser_invalidInput_thenUserNotCreated() throws Exception {
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("Test Password");
        userPostDTO.setUsername("testUsername");
        given(userService.createUser(any())).willThrow(new UserExcep("this username is not unique"));
        MockHttpServletRequestBuilder postRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));
        mockMvc.perform(postRequest).andExpect(status().isConflict());
    }

    @Test
    public void getUserByValidId_thenReturnsJsonArray() throws Exception {
        User user = new User();
        user.setUsername("Mario");
        user.setId(1L);
        user.setPassword("Admin");
        given(userService.findUserById(1L)).willReturn(user);

        MockHttpServletRequestBuilder getRequest = get("/users/1").contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.id", is(user.getId().intValue())));


    }

    @Test
    public void getUserByInvalidId_thenReturnsError() throws Exception {
        given(userService.findUserById(2)).willThrow(new EntityNotFoundExcep("not found", ""));

        MockHttpServletRequestBuilder getRequest = get("/users/2").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest).andExpect(status().isNotFound());

    }

    @Test
    public void loginWithValidInput_UserLoggedIn() throws Exception {
        User user = new User();
        user.setUsername("Mario");
        user.setPassword("Admin");
        user.setId(1L);

        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setUsername("Mario");
        userPutDTO.setPassword("Admin");

        given(userService.loginUser(any())).willReturn(user);

        MockHttpServletRequestBuilder putRequest = put("/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(userPutDTO));

        mockMvc.perform(putRequest)
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }

    @Test
    public void loginWithInvalidUsername_NotFound() throws Exception {
        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setUsername("Mario");
        userPutDTO.setPassword("Admin");
        given(userService.loginUser(any())).willThrow(new EntityNotFoundExcep("user not found", "ID"));

        MockHttpServletRequestBuilder putRequest = put("/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(userPutDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    public void loginWithInvalidPassword_Conflict() throws Exception {
        User user = new User();
        user.setUsername("Mario");
        user.setPassword("Admin");

        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setUsername("Mario");
        userPutDTO.setPassword("nimbA");

        given(userService.loginUser(any())).willThrow(new UserExcep("invalid password"));

        MockHttpServletRequestBuilder putRequest = put("/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(userPutDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isConflict());
    }

    //ToDO: test valid and Invalid logout (2Tests)
//F2
    @Ignore
    public void logoutTest() throws Exception {
        User user = new User();
        user.setUsername("Sara");
        user.setPassword("***");

        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setPassword("***");
        userPutDTO.setUsername("Sara");

        //given(userService.logoutUser()).willThrow();

    }

    @Test
    //F3
    public void AuthenticationTest() throws Exception {
        User user = new User();
        user.setUsername("Sara");
        user.setPassword("***");
        user.setToken("random");

        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setPassword("***");
        userPutDTO.setUsername("Sara");

        given(userService.checkAuth(user)).willReturn(true);
        MockHttpServletRequestBuilder putRequest = put("/users/auth").contentType(MediaType.APPLICATION_JSON).content(asJsonString(userPutDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isConflict()); //???

    }

    //F4
    @Test
    public void AuthenticationTestWithException() throws Exception {
        User user = new User();
        user.setUsername("Sara");
        user.setPassword("***");
        user.setToken("random");

        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setPassword("***");
        userPutDTO.setUsername("Sara");

        given(userService.checkAuth(user)).willThrow(new UserExcep("user cannot perform that"));
        MockHttpServletRequestBuilder putRequest = put("/users/auth").contentType(MediaType.APPLICATION_JSON).content(asJsonString(userPutDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isConflict());
    }

    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}

