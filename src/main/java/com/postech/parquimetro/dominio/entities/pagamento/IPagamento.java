package com.postech.parquimetro.dominio.entities.pagamento;

public sealed interface IPagamento permits Credito, Debito, Pix {

	IPagamento criarPagamento();
}
