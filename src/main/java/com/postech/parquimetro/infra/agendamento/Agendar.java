package com.postech.parquimetro.infra.agendamento;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.*;

public class Agendar {

	public void porTempoFixo(String nomeDoEvento, String agendamento, String body, String enderecoDaFila) {
		var eventBridge = EventBridgeClient.builder().region(Region.US_EAST_1).build();

		PutRuleRequest eventoDeAgendamento = PutRuleRequest.builder()
			.name(nomeDoEvento)
			.eventBusName("default")
			.scheduleExpression(agendamento)
			.state("ENABLED")
			.description("Agendamento para contabilizar o tempo fixo do parquimetro")
			.build();

		eventBridge.putRule(eventoDeAgendamento);

		Target enviarParaSQS = Target.builder()
			.id(nomeDoEvento)
			.arn(enderecoDaFila)
			.input(body)
			.build();

		PutTargetsRequest eventParaEnviarAoSqs = PutTargetsRequest.builder()
			.eventBusName("default")
			.targets(enviarParaSQS)
			.rule(nomeDoEvento)
			.build();

		eventBridge.putTargets(eventParaEnviarAoSqs);
	}

	public void porTempoVariavel(String nomeDoEvento, String body, String tempoDeAlerta, String enderecoDaFila) {

		var evento = EventBridgeClient.builder().region(Region.US_EAST_1).build();


		PutRuleRequest eventoDeAgendamento = PutRuleRequest.builder()
			.name(nomeDoEvento)
			.eventBusName("default")
			.scheduleExpression(tempoDeAlerta)
			.state("ENABLED")
			.description("Agendamento para contabilizar o tempo variavel do parquimetro")
			.build();

		evento.putRule(eventoDeAgendamento);

		Target enviarParaSQS = Target.builder()
			.id(nomeDoEvento)
			.arn(enderecoDaFila)
			.input(body)
			.build();

		PutTargetsRequest eventoParaEnviarAoSqs = PutTargetsRequest.builder()
			.eventBusName("default")
			.targets(enviarParaSQS)
			.rule(nomeDoEvento)
			.build();

		evento.putTargets(eventoParaEnviarAoSqs);
	}
}
