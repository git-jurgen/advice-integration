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
public class AmendmentCancellationWorkflow {
    private final OperationHandlerRegistry operationHandlerRegistry;

    public void execute(AdviceRequest adviceRequest) {
        ResolvedOperationHandlers resolvedOperationHandlers = operationHandlerRegistry
                .resolveAll(adviceRequest, WorkflowContext.SIMULATION);

        Map.Entry<OperationType, OperationHandler> firstEntry = resolvedOperationHandlers.getFirstEntry();
        log.info("Running first cancellation for operation type: {}", firstEntry.getKey());
        firstEntry.getValue().deleteOperation(adviceRequest.getContractNumber());

        resolvedOperationHandlers.getRemainingEntries().forEach((operationType, handler) -> {
            log.info("Running remaining cancellations for operation type: {}", operationType);
            handler.deleteOperation(adviceRequest.getContractNumber());
        });
    }
}
