package dmd.test.service;

import dmd.test.model.Role;
import dmd.test.model.UserRoles;
import dmd.test.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepo;

    @Autowired
    RoleServiceImpl(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public Role findByRole(UserRoles role) {
        return roleRepo.findByRole(role);
    }
    
}
