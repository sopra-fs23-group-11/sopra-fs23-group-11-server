package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.UserExcep;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


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
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserExcep("No users found");
        }
        return users;
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        newUser.setTotalWins(0);
        checkIfUserExists(newUser);
        newUser = userRepository.save(newUser);
        userRepository.flush();

        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

//    public User setNewName(User user){
//        User nUser = userRepository.getOne(user.getId());
//        User userByUsername = userRepository.findByUsername(user.getUsername());
//        if (userByUsername!=null){
//            throw new UserExcep("Username already taken");
//        }else{
//            nUser.setUsername(user.getUsername());
//            userRepository.flush();
//            return nUser;
//        }
//    }

    public void logoutUser(User user){
        User user1 = userRepository.findByUsername(user.getUsername());
        if (user1==null)
            throw new EntityNotFoundExcep("User not found", "");
        user1.setStatus(UserStatus.OFFLINE);
        userRepository.save(user1);
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
        if(Objects.equals(user.getPassword(), logUser.getPassword())){
            logUser.setToken(UUID.randomUUID().toString());
            logUser.setStatus(UserStatus.ONLINE);
            userRepository.flush();
            return logUser;}
        else{throw new UserExcep("Wrong password");
        }
    }

    private void checkIfUserExists(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
        if (userByUsername != null ) {
            throw new UserExcep("This username is not unique");
        }
    }

    public User findUserById(long id) {
        User userById = userRepository.getOne(id);
        if (userById != null) {return userById;}
        else {throw new EntityNotFoundExcep("User doesn't exist", "");}
    }

    private void checkIfUserExistsLogin(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
        if (userByUsername == null) {
            throw new EntityNotFoundExcep("User cannot be found by the provided name", "");
        }
    }

//    public User getUserByUsername(String username){
//        User user = userRepository.findByUsername(username);
//        user.setToken(UUID.randomUUID().toString());
//        user.setStatus(UserStatus.ONLINE);
//        return userRepository.findByUsername(username);
//    }


}
