package com.postech.parquimetro.infra.criptografia;

import jakarta.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Criptografia implements AttributeConverter<String,String> {

	private CriptografiaUtil criptografiaUtil;

	@Autowired
	public Criptografia(CriptografiaUtil encryptionUtil){
		this.criptografiaUtil = encryptionUtil;
	}

	@Override
	public String convertToDatabaseColumn(String valor) {
		return this.criptografiaUtil.criptografar(valor)
			.orElseThrow(() -> new RuntimeException("Nao foi possivel criptografar os valores"));

	}

	@Override
	public String convertToEntityAttribute(String s) {
		return this.criptografiaUtil.descriptografar(s)
			.orElseThrow(() -> new RuntimeException("Nao foi possivel descriptografar os valores"));
	}
}
