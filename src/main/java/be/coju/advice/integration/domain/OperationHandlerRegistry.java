package be.coju.advice.integration.domain;

import be.coju.advice.integration.web.AdviceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationHandlerRegistry {
    private final List<OperationHandler> operationHandlers;
    private final List<OperationResolver> operationResolvers;

    public OperationHandler resolve(AdviceRequest adviceRequest) {
        return resolve(adviceRequest, WorkflowContext.SIMULATION);
    }

    public OperationHandler resolve(AdviceRequest adviceRequest, WorkflowContext workflowContext) {
        var operationTypes = adviceRequest.getOperationTypes();
        var primaryType = operationTypes.stream()
                .filter(type -> operationResolvers.stream().anyMatch(resolver -> resolver.supports(type)))
                .findFirst()
                .orElseGet(() -> operationTypes.stream()
                        .filter(type -> operationHandlers.stream().anyMatch(handler -> handler.supports(type)))
                        .findFirst()
                        .orElse(operationTypes.isEmpty() ? null : operationTypes.getFirst()));

        log.info("Resolving operation handler for request with operation types: {} in workflow {}. Selected: {}", operationTypes, workflowContext, primaryType);

        if (primaryType == null) {
            throw new IllegalArgumentException("No operation types provided in advice request");
        }

        return operationResolvers.stream()
                .filter(resolver -> operationTypes.stream().anyMatch(resolver::supports))
                .findFirst()
                .map(resolver -> resolver.resolve(adviceRequest, workflowContext))
                .orElseGet(() -> operationHandlers.stream()
                        .filter(handler -> operationTypes.stream().anyMatch(handler::supports))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No handler found for operation type: " + primaryType)));
    }
}
