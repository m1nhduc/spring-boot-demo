package dmd.test.service;

import java.util.List;

import javax.validation.Valid;

import dmd.test.controller.AdminSignupFormCommand;
import dmd.test.model.Role;
import dmd.test.model.User;

public interface UserService {

    User getUserByEmail(String email);

    User create(User user);

    List<User> findAll();

    User registerAdmin(@Valid AdminSignupFormCommand command, List<Role> roles);

}
