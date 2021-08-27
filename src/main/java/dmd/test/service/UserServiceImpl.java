package dmd.test.service;

import dmd.test.exception.CustomException;
import dmd.test.exception.EntityType;
import dmd.test.exception.ExceptionType;
import dmd.test.model.User;
import dmd.test.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;

    @Autowired
    UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
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
    public User signup(User user) {
        User existedUser = userRepo.findByEmail(user.getEmail());
        if (existedUser == null) {
            return userRepo.save(user);
        }
        throw exception(EntityType.USER, ExceptionType.DUPLICATE_ENTITY, user.getEmail());
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return CustomException.throwException(entityType, exceptionType, args);
    }
}
