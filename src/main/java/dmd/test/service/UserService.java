package dmd.test.service;

import dmd.test.model.User;

public interface UserService {

    User getUserByEmail(String email);

    User signup(User user);

}
