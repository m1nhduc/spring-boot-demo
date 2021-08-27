package dmd.test.controller;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AdminSignupFormCommand {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 2)
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
