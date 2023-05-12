package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.UserExcep;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testName");
        testUser.setPassword("***");
        testUser.setAvatar("avatar");
        //testUser.setStatus(UserStatus.ONLINE);

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
    }

    @Test //
    public void createUser_validInputs_success() {

        User createdUser = userService.createUser(testUser);

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testUser.getId(), createdUser.getId());
        assertEquals(testUser.getUsername(), createdUser.getUsername());
        assertEquals(testUser.getUsername(), createdUser.getUsername());
        assertNotNull(createdUser.getToken());
        assertEquals(UserStatus.ONLINE, createdUser.getStatus());
        assertEquals(testUser.getAvatar(), createdUser.getAvatar());
    }

    @Test
    public void createUser_duplicateName_throwsException() {
        userService.createUser(testUser);
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);
        assertThrows(UserExcep.class, () -> userService.createUser(testUser));
    }

    @Test
    public void getUsersTest() {
        List<User> userList = new ArrayList<>();
        userList.add(testUser);

        Mockito.when(userRepository.findAll()).thenReturn(userList);
        List<User> users = userService.getUsers();

        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals(testUser.getUsername(), users.get(0).getUsername());
        Assertions.assertEquals(testUser.getPassword(), users.get(0).getPassword());

    }

    @Test
    public void getUsersTest_noUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(UserExcep.class, () -> userService.getUsers());
    }

    @Test
    void testLogoutUser() {

        Mockito.when(userRepository.findByUsername(testUser.getUsername())).thenReturn(testUser);
        userService.logoutUser(testUser);
        Mockito.verify(userRepository).save(testUser);
        Assertions.assertEquals(UserStatus.OFFLINE, testUser.getStatus());
    }

    @Test
    public void testLogoutUser_NotFound() {

        Mockito.when(userRepository.findByUsername(testUser.getUsername())).thenReturn(null);
        assertThrows(EntityNotFoundExcep.class, () -> userService.logoutUser(testUser));
    }

    @Test
    public void checkAuthTest() {
        testUser.setId(1L);
        testUser.setToken("token");

        Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(testUser);
        Assertions.assertTrue(userService.checkAuth(testUser));

        User invalidUser = new User();
        invalidUser.setId(1L);
        invalidUser.setToken("invalid");
        Assertions.assertFalse(userService.checkAuth(invalidUser));
    }

    @Test
    public void logInTest() {
        User testUser2 = new User();
        testUser2.setId(1L);
        testUser2.setUsername("testName");
        testUser2.setPassword("***");

        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(testUser2);

        User loggedInUser = userService.loginUser(testUser);
        Assertions.assertNotNull(loggedInUser.getToken());
        Assertions.assertEquals(UserStatus.ONLINE, loggedInUser.getStatus());

    }

    @Test
    public void logInTest_NotFound() {
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(null);
        assertThrows(EntityNotFoundExcep.class, () -> userService.loginUser(testUser));
    }


    @Test
    void testFindUserById() {

        Mockito.when(userRepository.getOne(testUser.getId())).thenReturn(testUser);

        User userToReturn = userService.findUserById(testUser.getId());

        Assertions.assertNotNull(userToReturn);
        Assertions.assertEquals(testUser.getUsername(), userToReturn.getUsername());
        Assertions.assertEquals(testUser.getId(), userToReturn.getId());
    }


/*
  @Test
  public void createUser_duplicateInputs_throwsException() {
    // given -> a first user has already been created
    userService.createUser(testUser);

    // when -> setup additional mocks for UserRepository
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    // then -> attempt to create second user with same user -> check that an error
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }


 */
}
