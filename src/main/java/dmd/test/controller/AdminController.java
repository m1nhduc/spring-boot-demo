
package dmd.test.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import dmd.test.model.Role;
import dmd.test.model.User;
import dmd.test.model.UserRoles;
import dmd.test.service.RoleService;
import dmd.test.service.UserService;

@Controller
public class AdminController {
    private UserService userService;
    private RoleService roleService;
    private BCryptPasswordEncoder encoder;

    @Autowired
    AdminController(UserService userService, RoleService roleService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @GetMapping("/admin-login")
    public ModelAndView loginGET() {
        ModelAndView mav = new ModelAndView("admin-login");
        return mav;
    }

    @GetMapping("/admin-dashboard")
    public ModelAndView dashboardGET() {
        return new ModelAndView("dashboard");
    }

    @GetMapping("/admin-signup")
    public ModelAndView signupGET() {
        ModelAndView mav = new ModelAndView("admin-signup");
        mav.addObject("adminSignupFormData", new AdminSignupFormCommand());
        return mav;
    }

    @PostMapping("/admin-signup")
    public ModelAndView signupPOST(@Valid @ModelAttribute("adminSignupFormData") AdminSignupFormCommand command, BindingResult br) {
        ModelAndView mav = new ModelAndView("admin-signup");
        if (br.hasErrors()) {
            return mav;
        }
        try {
            User newUser = registerAdmin(command);
        } catch (Exception e) {
            br.rejectValue("email", "ERR.AdminSignupFormCommand", e.getMessage());
            return mav;
        }
        return new ModelAndView("admin-signup");
    }

    private User registerAdmin(@Valid AdminSignupFormCommand command) {
        User userInput = setSignupInputData(command);
        User newUser = userService.signup(userInput);
        return newUser;
    }

    private User setSignupInputData(AdminSignupFormCommand command) {
        User user = new User();
        user.setEmail(command.getEmail());
        user.setPassword(encoder.encode(command.getPassword()));
        user.setFirstName(command.getFirstName());
        user.setLastName(command.getLastName());
        user.setRoles(getSignupRolesData());
        return user;
    }

    private List<Role> getSignupRolesData() {
        List<Role> roles = new ArrayList<Role>();
        roles.add(roleService.findByRole(UserRoles.ADMIN));
        return roles;
    }
}
