package com.postech.parquimetro.dominio.entities.pagamento;

import org.springframework.stereotype.Component;


public sealed interface IPagamento<T> permits Credito, Debito, Pix {

	T criarPagamento();
}
