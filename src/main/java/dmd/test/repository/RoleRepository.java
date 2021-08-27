package dmd.test.repository;

import dmd.test.model.Role;
import dmd.test.model.UserRoles;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(UserRoles role);
}
