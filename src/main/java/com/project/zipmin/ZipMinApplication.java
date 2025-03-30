package com.project.zipmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@SpringBootApplication

public class ZipMinApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipMinApplication.class, args);
		
		// String password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("1234");
	}

}
