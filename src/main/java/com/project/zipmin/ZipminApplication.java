package com.project.zipmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ZipminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipminApplication.class, args);
		// String password = new BCryptPasswordEncoder().encode("1234");
	}

}
