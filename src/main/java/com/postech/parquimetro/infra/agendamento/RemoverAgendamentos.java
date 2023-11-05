package com.postech.parquimetro.infra.agendamento;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.*;

public class RemoverAgendamentos {

	public void remover(String ruleID) {

		var eventBridge = EventBridgeClient.builder().region(Region.US_EAST_1).build();

		RemoveTargetsRequest removerAlvos = RemoveTargetsRequest.builder()
			.eventBusName("default")
			.force(true)
			.rule(ruleID)
			.ids(ruleID)
			.build();

		DeleteRuleRequest removerAgendamento = DeleteRuleRequest.builder()
			.eventBusName("default")
			.name(ruleID)
			.force(true)
			.build();

		eventBridge.removeTargets(removerAlvos);
		eventBridge.deleteRule(removerAgendamento);
		eventBridge.close();
	}

}
