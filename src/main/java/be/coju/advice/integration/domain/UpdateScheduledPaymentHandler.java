package be.coju.advice.integration.domain;

import be.coju.advice.integration.web.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class UpdateScheduledPaymentHandler extends AbstractOperationHandler {
    @Override
    public void addOperation(String contractNumber) {
        log.info("Adding operation to update scheduled payment handler");
    }

    @Override
    public void preparePatchOperation(String contractNumber) {
        log.info("Preparing operation to update scheduled payment handler");
    }

    @Override
    public void patchOperation(String contractNumber) {
        log.info("Patching operation to update scheduled payment handler");
    }

    @Override
    public void executeOperation(String contractNumber) {
        log.info("Executing operation to update scheduled payment handler");
    }

    @Override
    public BigDecimal calculateCosts(String contractNumber) {

        log.info("Calculating costs for update scheduled payment handler");
        return BigDecimal.valueOf(100);
    }

    @Override
    public void deleteOperation(String contractNumber) {
        log.info("Deleting operation to update scheduled payment handler");
    }
}
