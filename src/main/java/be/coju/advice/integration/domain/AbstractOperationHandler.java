package be.coju.advice.integration.domain;

import be.coju.advice.integration.web.OperationType;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public abstract class AbstractOperationHandler implements OperationHandler {
    @Override
    public boolean supports(OperationType operationType) {
        return false;
    }

    @Override
    public void createOperation(String contractNumber) {
        addOperation(contractNumber);
        preparePatchOperation(contractNumber);
        patchOperation(contractNumber);
    }

    @Override
    public void executeOperation(String contractNumber) {
        // Default implementation (can be overridden by subclasses)
    }

    @Override
    public void startBpmProcess(String contractNumber, OperationType operationType) {
        log.info("Starting BPM process for contract {} and operation type {}", contractNumber, operationType);
    }

    @Override
    public BigDecimal calculateCosts(String contractNumber) {
        // Default implementation (can be overridden by subclasses)
        return BigDecimal.ZERO;
    }

    @Override
    public abstract void deleteOperation(String contractNumber);

    public abstract void addOperation(String contractNumber);
    public abstract void preparePatchOperation(String contractNumber);
    public abstract void patchOperation(String contractNumber);
}
