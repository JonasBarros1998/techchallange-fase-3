package com.postech.parquimetro.infra.criptografia;

import jakarta.validation.Valid;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


@Component
public class CriptografiaUtil {

	@Value("${seguranca.chave}")
	private String key;

	@Value("${seguranca.algoritimo}")
	private String algoritimo;

	@Value("${seguranca.initVector}")
	private String initVector;


	public Optional<String> descriptografar(String valorCriptografado) {

		try {
			IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes("UTF-8"));

			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

			Cipher cipher = Cipher.getInstance(algoritimo);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
			byte[] original = cipher.doFinal(Base64.decodeBase64(valorCriptografado));
			System.out.println(new String(original));
			return Optional.of(new String(original));

		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	public Optional<String> criptografar(String valor) {

		try {
			IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes("UTF-8"));

			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

			Cipher cipher = Cipher.getInstance(algoritimo);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);

			byte[] encrypted = cipher.doFinal(valor.getBytes());

			return Optional.of(Base64.encodeBase64String(encrypted));
		}
		catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}

	}
}
