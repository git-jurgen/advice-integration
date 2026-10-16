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
public class AmendmentCancellationWorkflow {
    private final OperationHandlerRegistry operationHandlerRegistry;

    public void execute(AdviceRequest adviceRequest) {
        adviceRequest.getOperationTypes().forEach(operationType -> runCancellation(adviceRequest.forOperationType(operationType)));
    }

    private void runCancellation(AdviceRequest adviceRequest) {
        log.info("Running cancellation for operation types: {}", adviceRequest.getOperationTypes());
        OperationHandler handler = operationHandlerRegistry.resolve(adviceRequest);

        handler.deleteOperation(adviceRequest.getContractNumber());
    }
}
