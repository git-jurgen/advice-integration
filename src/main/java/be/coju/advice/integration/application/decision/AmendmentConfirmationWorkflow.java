package be.coju.advice.integration.application.decision;

import be.coju.advice.integration.domain.OperationHandler;
import be.coju.advice.integration.domain.OperationHandlerRegistry;
import be.coju.advice.integration.web.AdviceRequest;
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
        OperationHandler handler = operationHandlerRegistry.resolve(adviceRequest);

        handler.createOperation(adviceRequest.getContractNumber());
        handler.executeOperation(adviceRequest.getContractNumber());
        handler.startBpmProcess(adviceRequest.getContractNumber(), adviceRequest.getOperationTypes().getFirst());
    }
}
