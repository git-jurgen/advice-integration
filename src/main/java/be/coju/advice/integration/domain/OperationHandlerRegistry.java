package be.coju.advice.integration.domain;

import be.coju.advice.integration.web.AdviceRequest;
import be.coju.advice.integration.web.OperationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationHandlerRegistry {
    private final List<OperationHandler> operationHandlers;
    private final List<OperationResolver> operationResolvers;

    public ResolvedOperationHandlers resolveAll(AdviceRequest adviceRequest, WorkflowContext workflowContext) {
        Map<OperationType, OperationHandler> handlers = new LinkedHashMap<>();
        adviceRequest.getOperationTypes().stream()
                .distinct()
                .sorted(Comparator.comparingInt(OperationType::getPriority))
                .forEach(operationType -> {
                    AdviceRequest operationRequest = adviceRequest.forOperationType(operationType);
                    log.info("Resolving operation handler for type: {} in workflow {}", operationType, workflowContext);

                    OperationHandler handler = resolveOperationHandler(workflowContext, operationType, operationRequest);
                    handlers.put(operationType, handler);
                });

        if (handlers.isEmpty()) {
            throw new IllegalArgumentException("No operation types provided in advice request");
        }
        return new ResolvedOperationHandlers(handlers);
    }

    private OperationHandler resolveOperationHandler(WorkflowContext workflowContext, OperationType operationType, AdviceRequest operationRequest) {
        return operationResolvers.stream()
                .filter(resolver -> resolver.supports(operationType))
                .findFirst()
                .map(resolver -> resolver.resolve(operationRequest, workflowContext))
                .orElseGet(() -> getOperationHandler(operationType));
    }

    private OperationHandler getOperationHandler(OperationType operationType) {
        return operationHandlers.stream()
                .filter(candidate -> candidate.supports(operationType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No handler found for operation type: " + operationType));
    }
}
