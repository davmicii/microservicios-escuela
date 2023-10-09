package com.escuela.authservice;

import com.escuela.authservice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication{


	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}


}
