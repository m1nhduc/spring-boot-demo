package dmd.test.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dmd.test.controller.AdminSignupFormCommand;
import dmd.test.exception.CustomException;
import dmd.test.exception.EntityType;
import dmd.test.exception.ExceptionType;
import dmd.test.model.Role;
import dmd.test.model.User;
import dmd.test.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;
    private BCryptPasswordEncoder encoder;

    @Autowired
    UserServiceImpl(UserRepository userRepo, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Transactional
    public User getUserByEmail(String email) {
        User existedUser = userRepo.findByEmail(email);
        if (existedUser != null) {
            return existedUser;
        }
        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, email);
    }

    @Override
    public User create(User userInput) {
        User existedUser = userRepo.findByEmail(userInput.getEmail());
        if (existedUser == null) {
            return userRepo.save(userInput);
        }
        throw exception(EntityType.USER, ExceptionType.DUPLICATE_ENTITY, userInput.getEmail());
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return CustomException.throwException(entityType, exceptionType, args);
    }

    @Override
    public User registerAdmin(@Valid AdminSignupFormCommand command, List<Role> roles) {
        User userInput = setSignupInputData(command, roles);
        User newUser = create(userInput);
        return newUser;
    }

    private User setSignupInputData(AdminSignupFormCommand command, List<Role> roles) {
        User user = new User();
        user.setEmail(command.getEmail());
        user.setPassword(encoder.encode(command.getPassword()));
        user.setFirstName(command.getFirstName());
        user.setLastName(command.getLastName());
        user.setRoles(roles);
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> ls = userRepo.findAll();
        return ls;
    }
}
