package com.PiratesOfTheSiliconValley.LibSys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class LibSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibSysApplication.class, args);
	}

}
