package be.coju.advice.integration.application.simulation;

import be.coju.advice.integration.domain.OperationHandler;
import be.coju.advice.integration.domain.OperationHandlerRegistry;
import be.coju.advice.integration.domain.ResolvedOperationHandlers;
import be.coju.advice.integration.domain.WorkflowContext;
import be.coju.advice.integration.web.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmendmentSimulationWorkflow {
    private final OperationHandlerRegistry operationHandlerRegistry;

    public SimulationResponse execute(AdviceRequest adviceRequest) {
        ResolvedOperationHandlers resolvedOperationHandlers = operationHandlerRegistry.resolveAll(adviceRequest, WorkflowContext.SIMULATION);

        List<Simulation> simulations = resolvedOperationHandlers.getHandlers().entrySet().stream()
                .map(entry -> runSimulation(entry, adviceRequest.forOperationType(entry.getKey())))
                .toList();

        return SimulationResponse.builder()
                .simulations(simulations)
                .build();
    }

    private Simulation runSimulation(Map.Entry<OperationType, OperationHandler> handler, AdviceRequest adviceRequest) {
        log.info("Running simulation for operation types: {}", adviceRequest.getOperationTypes());

        handler.getValue().createOperation(adviceRequest.getContractNumber());
        BigDecimal costs = handler.getValue().calculateCosts(adviceRequest.getContractNumber());

        return Simulation.builder()
                .operationType(handler.getKey())
                .costs(List.of(Cost.builder().costType("CostA").amount(costs).build()))
                .build();
    }
}
