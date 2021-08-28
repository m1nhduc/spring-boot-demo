package dmd.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dmd.test.repository.RoleRepository;
import dmd.test.repository.UserRepository;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository) {
		return args -> {
			// Create Admin and Passenger Roles
			// Role adminRole = roleRepository.findByRole(UserRoles.ADMIN);
			// if (adminRole == null) {
			// 	adminRole = new Role();
			// 	adminRole.setRole(UserRoles.ADMIN);
			// 	roleRepository.save(adminRole);
			// }

			// Role userRole = roleRepository.findByRole(UserRoles.USER);
			// if (userRole == null) {
			// 	userRole = new Role();
			// 	userRole.setRole(UserRoles.USER);
			// 	roleRepository.save(userRole);
			// }

			// String email = "test1@test.test";
			// User user = userRepository.findByEmail(email);
			// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>-----------------1--------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			// Collection<Role> ls = user.getRoles();
			// ls.forEach(role -> {
			// 	System.out.println(role.getRole());
			// });
			// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>-----------------2--------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		};
	}

}
