package dmd.test.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dmd.test.model.Role;
import dmd.test.model.User;
import dmd.test.service.UserService;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private UserService userService;
    
    @Autowired
    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            List<GrantedAuthority> lsAuthority = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, lsAuthority);
        }
        throw new UsernameNotFoundException("not found account using email: " + email);
    }

    private List<GrantedAuthority> getUserAuthority(Collection<Role> roles) {
        Set<GrantedAuthority> rolesSet = new HashSet<>();
        roles.forEach((role) -> {
            rolesSet.add(new SimpleGrantedAuthority(role.getRole().toString()));
        });
        return new ArrayList<GrantedAuthority>(rolesSet);
    }

    private UserDetails buildUserForAuthentication(User user, Collection<GrantedAuthority> lsAuthority) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), lsAuthority);
    }

}