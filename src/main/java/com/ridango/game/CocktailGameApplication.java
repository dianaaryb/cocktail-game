package com.ridango.game;

import org.json.simple.parser.ParseException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class CocktailGameApplication implements CommandLineRunner {

	public static void main(String[] args) throws IOException, ParseException {
		SpringApplication.run(CocktailGameApplication.class, args);
		Engine engine = new Engine();
		engine.run();
	}

	@Override public void run(String... args) throws Exception {
	}
}
