package com.xpresspayments.xpress;

import com.xpresspayments.xpress.models.Authority;
import com.xpresspayments.xpress.models.User;
import com.xpresspayments.xpress.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

@SpringBootApplication
public class XpressApplication {

	public static void main(String[] args) {
		SpringApplication.run(XpressApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder){
		return (args)->{
			User user = new User();
			user.setUsername("jerry@email.com");
			user.setPassword(passwordEncoder.encode("password"));
			user.setAuthorities(Set.of(Authority.USER));
			userRepository.save(user);
		};
	}

	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*");
			}
		};
	}

}
