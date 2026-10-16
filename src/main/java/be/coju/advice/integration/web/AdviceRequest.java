package be.coju.advice.integration.web;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class AdviceRequest {
    private String contractNumber;
    private boolean hasScheduledPayment;

    @JsonAlias({"operations", "operationTypes", "operationType"})
    private List<Operation> operations;

    @JsonCreator
    public AdviceRequest(
            @JsonProperty("contractNumber") String contractNumber,
            @JsonProperty("hasScheduledPayment") boolean hasScheduledPayment,
            @JsonProperty("operations") List<Operation> operations) {
        this.contractNumber = contractNumber;
        this.hasScheduledPayment = hasScheduledPayment;
        this.operations = operations;
    }

    public boolean hasScheduledPayment() {
        return hasScheduledPayment;
    }

    public List<OperationType> getOperationTypes() {
        return operations.stream()
                .map(Operation::getOperationType)
                .toList();
    }

    public AdviceRequest forOperationType(OperationType operationType) {
        return AdviceRequest.builder()
                .contractNumber(contractNumber)
                .hasScheduledPayment(hasScheduledPayment)
                .operations(getOperationsOfOperationType(operationType))
                .build();
    }

    private List<Operation> getOperationsOfOperationType(OperationType operationType) {
        return operations.stream()
                .filter(op -> op.getOperationType().equals(operationType))
                .toList();
    }
}
