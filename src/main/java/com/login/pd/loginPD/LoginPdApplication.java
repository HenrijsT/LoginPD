package com.login.pd.loginPD;

import com.login.pd.loginPD.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class LoginPdApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginPdApplication.class, args);
	}

}
