package com.examportal;

import com.examportal.helper.UserFoundException;
import com.examportal.models.Role;
import com.examportal.models.User;
import com.examportal.models.UserRole;
import com.examportal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ExamportalApplication implements CommandLineRunner {
	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		System.out.println("Starting Exam Portal..1.");
		SpringApplication.run(ExamportalApplication.class, args);
		System.out.println("All Good with Exam Portal.");
	}

	@Override
	public void run(String... args) throws Exception {
		try {
////Creating ADMIN
			User user = new User();
			user.setUsername("amar");
			user.setFirstname("am");
			user.setLastname("ar");
			user.setPassword(this.bCryptPasswordEncoder.encode("amar@pass"));
			user.setEmail("amar@gmail.com");
			user.setPhone("7719920046");
			user.setProfilePic("amar.png");

			Role role1 = new Role();
			role1.setRoleId(44L);
			role1.setRoleName("ADMIN");

			Set<UserRole> userRoleSet = new HashSet<>();

			UserRole userRole = new UserRole();

			userRole.setRole(role1);
			userRole.setUser(user);

			userRoleSet.add(userRole);

			User user1 = this.userService.createUser(user, userRoleSet);
			System.out.println(user1.getUsername());
		}
		catch(UserFoundException ur){
			ur.printStackTrace();
		}

	}
}
