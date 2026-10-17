package be.coju.advice.integration.application.decision;

import be.coju.advice.integration.domain.OperationHandler;
import be.coju.advice.integration.domain.OperationHandlerRegistry;
import be.coju.advice.integration.domain.ResolvedOperationHandlers;
import be.coju.advice.integration.domain.WorkflowContext;
import be.coju.advice.integration.web.AdviceRequest;
import be.coju.advice.integration.web.OperationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmendmentConfirmationWorkflow {
    private final OperationHandlerRegistry operationHandlerRegistry;

    public void execute(AdviceRequest adviceRequest) {
        ResolvedOperationHandlers resolvedOperationHandlers = operationHandlerRegistry.resolveAll(adviceRequest, WorkflowContext.CONFIRMATION);
        resolvedOperationHandlers.getHandlers().entrySet().forEach(entry -> runConfirmation(entry, adviceRequest.forOperationType(entry.getKey())));
    }

    private void runConfirmation(Map.Entry<OperationType, OperationHandler> entry, AdviceRequest adviceRequest) {
        log.info("Running confirmation for operation types: {}", adviceRequest.getOperationTypes());
        entry.getValue().createOperation(adviceRequest.getContractNumber());
        entry.getValue().executeOperation(adviceRequest.getContractNumber());
        entry.getValue().startBpmProcess(adviceRequest.getContractNumber(), entry.getKey());
    }
}
