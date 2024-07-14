package com.example.project.Coding_Project;

import org.springframework.boot.SpringApplication;

public class TestCodingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.from(CodingProjectApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
