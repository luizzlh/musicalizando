package com.alura.musicalizando;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alura.musicalizando.principal.Principal;
import com.alura.musicalizando.repositories.ArtistaRepository;

@SpringBootApplication
public class MusicalizandoApplication implements CommandLineRunner{
	@Autowired
	private ArtistaRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(MusicalizandoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio);
		principal.exibeMenu();
	}

}