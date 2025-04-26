package com.project.zipmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@SpringBootApplication
@MapperScan(basePackages = "com.project.zipmin.mapper")
public class ZipMinApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipMinApplication.class, args);
		// String password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("1234");
	}

}
