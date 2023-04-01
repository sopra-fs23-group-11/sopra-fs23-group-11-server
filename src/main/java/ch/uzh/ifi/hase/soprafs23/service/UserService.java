package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        newUser.setTotalWins(0);
        //newUser.setVoldemort(String.valueOf(java.time.LocalDate.now()));
        checkIfUserExists(newUser);
        // saves the given entity but data is only persisted in the database once
        // flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();

        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User setNewName(User user){
        User nUser = userRepository.getOne(user.getId());
        User userByUsername = userRepository.findByUsername(user.getUsername());
        if (userByUsername!=null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Username already taken"));
        }else{
            nUser.setUsername(user.getUsername());
            userRepository.flush();
            return nUser;
        }
    }

    public void logoutUser(User user){
        long id = user.getId();
        User userById = userRepository.getOne(id);
        userById.setStatus(UserStatus.OFFLINE);
        userRepository.flush();
    }

    public boolean checkAuth(User user){
        User userById = userRepository.getOne(user.getId());
        if (Objects.equals(userById.getToken(),user.getToken())){return true;}
        else{return false;}
    }

    public User loginUser(User user){
        checkIfUserExistsLogin(user);
        User logUser = userRepository.findByUsername(user.getUsername());
        logUser.setToken(UUID.randomUUID().toString());
        logUser.setStatus(UserStatus.ONLINE);
        userRepository.flush();
        return logUser;
    }

    /**
     * This is a helper method that will check the uniqueness criteria of the
     * username and the name
     * defined in the User entity. The method will do nothing if the input is unique
     * and throw an error otherwise.
     *
     * @param userToBeCreated
     * @throws org.springframework.web.server.ResponseStatusException
     * @see User
     */
    private void checkIfUserExists(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
        //User userByName = userRepository.findByName(userToBeCreated.getName());

        String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";
        /*
        if (userByUsername != null && userByName != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(baseErrorMessage, "username and the name", "are"));
        } else if (userByUsername != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "username", "is"));
        } else if (userByName != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "name", "is"));
        }*/
        if (userByUsername != null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(baseErrorMessage, "username", "is"));
        }
    }

    public User findUserById(long id) {
        User userById = userRepository.getOne(id);
        if (userById != null) {return userById;}
        else {throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
    }

    private void checkIfUserExistsLogin(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
        String baseErrorMessage = "Login failed: %s";
        if (userByUsername == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(baseErrorMessage, "Username does not exist"));
        }
    }

    public User getUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        user.setToken(UUID.randomUUID().toString());
        user.setStatus(UserStatus.ONLINE);
        return userRepository.findByUsername(username);
    }
}
