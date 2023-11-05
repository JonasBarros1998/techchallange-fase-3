package com.postech.parquimetro;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class ParquimetroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParquimetroApplication.class, args);
	}

	@PostConstruct
	public void init() {
		// Define o TimeZone padrão para UTC-3
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("America/Sao_Paulo")));
	}

}
