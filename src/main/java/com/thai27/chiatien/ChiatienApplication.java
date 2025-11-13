package com.thai27.chiatien;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ChiatienApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChiatienApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder passencode() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
