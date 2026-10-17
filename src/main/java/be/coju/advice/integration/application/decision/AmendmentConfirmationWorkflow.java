package be.coju.advice.integration.application.decision;

import be.coju.advice.integration.domain.OperationHandler;
import be.coju.advice.integration.domain.OperationHandlerRegistry;
import be.coju.advice.integration.domain.WorkflowContext;
import be.coju.advice.integration.web.AdviceRequest;
import be.coju.advice.integration.web.OperationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmendmentConfirmationWorkflow {
    private final OperationHandlerRegistry operationHandlerRegistry;

    public void execute(AdviceRequest adviceRequest) {
        adviceRequest.getOperationTypes().forEach(operationType -> runConfirmation(adviceRequest.forOperationType(operationType)));
    }

    private void runConfirmation(AdviceRequest adviceRequest) {
        log.info("Running confirmation for operation types: {}", adviceRequest.getOperationTypes());
        OperationType operationType = adviceRequest.getOperationTypes().getFirst();
        OperationHandler handler = operationHandlerRegistry
                .resolveAll(adviceRequest, WorkflowContext.CONFIRMATION)
                .getHandlers()
                .get(operationType);

        handler.createOperation(adviceRequest.getContractNumber());
        handler.executeOperation(adviceRequest.getContractNumber());
        handler.startBpmProcess(adviceRequest.getContractNumber(), operationType);
    }
}
