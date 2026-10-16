package be.coju.advice.integration.web;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class Simulation {
    private OperationType operationType;
    private List<Cost> costs;
}
