package be.coju.advice.integration.domain;

import be.coju.advice.integration.web.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class PartialSurrenderEndIgpHandler extends AbstractOperationHandler {
    @Override
    public boolean supports(OperationType operationType) {
        return OperationType.PARTIAL_SURRENDER_END_IGP.equals(operationType);
    }

    @Override
    public void addOperation(String contractNumber) {
        log.info("Adding operation to partial surrender end igp handler");
    }

    @Override
    public void preparePatchOperation(String contractNumber) {
        log.info("Preparing operation to partial surrender end igp handler");
    }

    @Override
    public void patchOperation(String contractNumber) {
        log.info("Patching operation to partial surrender end igp handler");
    }

    @Override
    public void executeOperation(String contractNumber) {
        log.info("Executing operation to partial surrender end igp handler");
    }

    @Override
    public BigDecimal calculateCosts(String contractNumber) {
        log.info("Calculating costs for partial surrender end igp handler");
        return BigDecimal.valueOf(40);
    }

    @Override
    public void deleteOperation(String contractNumber) {
        log.info("Deleting operation to partial surrender end igp handler");
    }
}
