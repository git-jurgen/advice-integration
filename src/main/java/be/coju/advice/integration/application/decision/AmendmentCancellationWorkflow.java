package be.coju.advice.integration.application.decision;

import be.coju.advice.integration.domain.OperationHandlerRegistry;
import be.coju.advice.integration.domain.ResolvedOperationHandlers;
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
        ResolvedOperationHandlers resolvedHandlers = operationHandlerRegistry.resolveAll(adviceRequest);
        resolvedHandlers.getHandlers().forEach((operationType, handler) -> {
            log.info("Running cancellation for operation type: {}", operationType);
            handler.deleteOperation(adviceRequest.getContractNumber());
        });
    }
}
