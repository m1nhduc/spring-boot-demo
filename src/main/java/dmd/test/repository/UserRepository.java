package dmd.test.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dmd.test.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    List<User> findAll();
}
