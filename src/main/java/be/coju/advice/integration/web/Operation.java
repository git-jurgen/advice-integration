package be.coju.advice.integration.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class Operation {
    private OperationType operationType;
    private BigDecimal amount;

    @JsonCreator
    public Operation(
            @JsonProperty("operationType") OperationType operationType,
            @JsonProperty("amount") BigDecimal amount) {
        this.operationType = operationType;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Operation{operationType=" + (operationType == null ? null : operationType.getLabel()) + ", amount=" + amount + '}';
    }
}
