package be.coju.advice.integration.application.simulation;

import be.coju.advice.integration.domain.OperationHandler;
import be.coju.advice.integration.domain.OperationHandlerRegistry;
import be.coju.advice.integration.web.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmendmentSimulationWorkflow {
    private final OperationHandlerRegistry operationHandlerRegistry;

    public SimulationResponse execute(AdviceRequest adviceRequest) {
        List<Simulation> simulations = adviceRequest.getOperationTypes().stream()
                .map(operationType -> runSimulation(adviceRequest.forOperationType(operationType)))
                .toList();

        return SimulationResponse.builder()
                .simulations(simulations)
                .build();
    }

    private Simulation runSimulation(AdviceRequest adviceRequest) {
        log.info("Running simulation for operation types: {}", adviceRequest.getOperationTypes());
        OperationHandler handler = operationHandlerRegistry.resolve(adviceRequest);

        handler.createOperation(adviceRequest.getContractNumber());
        BigDecimal costs = handler.calculateCosts(adviceRequest.getContractNumber());

        return Simulation.builder()
                .operationType(adviceRequest.getOperationTypes().getFirst())
                .costs(List.of(Cost.builder().costType("CostA").amount(costs).build()))
                .build();
    }
}
