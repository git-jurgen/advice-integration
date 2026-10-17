package be.coju.advice.integration.application.decision;

import be.coju.advice.integration.domain.OperationHandler;
import be.coju.advice.integration.domain.OperationHandlerRegistry;
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
public class AmendmentCancellationWorkflow {
    private final OperationHandlerRegistry operationHandlerRegistry;

    public void execute(AdviceRequest adviceRequest) {
        Map<OperationType, OperationHandler> handlers = operationHandlerRegistry
                .resolveAll(adviceRequest, WorkflowContext.SIMULATION)
                .getHandlers();
        handlers.forEach((operationType, handler) -> {
            log.info("Running cancellation for operation type: {}", operationType);
            handler.deleteOperation(adviceRequest.getContractNumber());
        });
    }
}
