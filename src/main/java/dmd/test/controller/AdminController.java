
package dmd.test.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dmd.test.model.Role;
import dmd.test.model.User;
import dmd.test.model.UserRoles;
import dmd.test.service.RoleService;
import dmd.test.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public ModelAndView loginGET() {
        ModelAndView mav = new ModelAndView("admin-login");
        return mav;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboardGET() {
        ModelAndView mav = new ModelAndView("admin-dashboard");
        List<User> userLs = userService.findAll();
        mav.addObject("userLs", userLs);
        return mav;
    }

    @GetMapping("/signup")
    public ModelAndView signupGET() {
        ModelAndView mav = new ModelAndView("admin-signup");
        mav.addObject("adminSignupFormData", new AdminSignupFormCommand());
        return mav;
    }

    @PostMapping("/signup")
    public ModelAndView signupPOST(@Valid @ModelAttribute("adminSignupFormData") AdminSignupFormCommand command, BindingResult br) {
        ModelAndView mav = new ModelAndView("admin-signup");
        if (br.hasErrors()) {
            return mav;
        }
        try {
            Role role = roleService.findByRole(UserRoles.ADMIN);
            List<Role> roles = new ArrayList<Role>();
            roles.add(role);
            User newUser = userService.registerAdmin(command, roles);
        } catch (Exception e) {
            br.rejectValue("email", "ERR.AdminSignupFormCommand", e.getMessage());
            return mav;
        }
        return new ModelAndView("admin-signup");
    }

    @GetMapping("/user/edit/{id}")
    public ModelAndView userEdit(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("admin-dashboard");
        return mav;
    }

    @GetMapping("/user/remove/{id}")
    public ModelAndView userRemove(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("admin-dashboard");
        return mav;
    }
}
