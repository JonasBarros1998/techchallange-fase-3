package com.postech.parquimetro.view.controller;

import com.postech.parquimetro.aplicacao.parquimetro.FinalizarAgendamentoPorTempoFixo;
import com.postech.parquimetro.view.form.parquimetro.FinalizarAgendamentoForm;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinalizarAgendamentosListener {

	FinalizarAgendamentoPorTempoFixo finalizarAgendamentoPorTempoFixo;

	@Autowired
	public FinalizarAgendamentosListener(FinalizarAgendamentoPorTempoFixo finalizarAgendamentoPorTempoFixo) {
		this.finalizarAgendamentoPorTempoFixo = finalizarAgendamentoPorTempoFixo;
	}

	public FinalizarAgendamentosListener() {}

	@SqsListener("postech_remover_evento")
	public void porTempoFixo(FinalizarAgendamentoForm eventosAgendadosForm) {
		this.finalizarAgendamentoPorTempoFixo.remover(eventosAgendadosForm);
	}
}
