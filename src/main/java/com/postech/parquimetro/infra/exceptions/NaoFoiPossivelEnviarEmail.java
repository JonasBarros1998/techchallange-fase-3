package com.postech.parquimetro.infra.exceptions;

public final class NaoFoiPossivelEnviarEmail extends RuntimeException {

	public NaoFoiPossivelEnviarEmail(String message) {
		super(message);
	}
}
