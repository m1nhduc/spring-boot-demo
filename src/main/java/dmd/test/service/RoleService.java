package dmd.test.service;

import dmd.test.model.Role;
import dmd.test.model.UserRoles;

public interface RoleService {
    Role findByRole(UserRoles userRole);
}
