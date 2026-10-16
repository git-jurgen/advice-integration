package be.coju.advice.integration.domain;

import be.coju.advice.integration.web.OperationType;

import java.math.BigDecimal;

public interface OperationHandler {
    boolean supports(OperationType operationType);

    void createOperation(String contractNumber);
    void executeOperation(String contractNumber);
    void deleteOperation(String contractNumber);

    void startBpmProcess(String contractNumber, OperationType operationType);

    BigDecimal calculateCosts(String contractNumber);
}
