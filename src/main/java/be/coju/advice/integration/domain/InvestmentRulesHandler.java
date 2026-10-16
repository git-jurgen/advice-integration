package be.coju.advice.integration.domain;

import be.coju.advice.integration.web.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class InvestmentRulesHandler extends AbstractOperationHandler {
    @Override
    public void addOperation(String contractNumber) {
        log.info("Adding operation to investmentrules handler");
    }

    @Override
    public void preparePatchOperation(String contractNumber) {
        log.info("Preparing operation to investmentrules handler");
    }

    @Override
    public void patchOperation(String contractNumber) {
        log.info("Patching operation to investmentrules handler");
    }

    @Override
    public void executeOperation(String contractNumber) {
        log.info("Executing operation to investmentrules handler");
    }

    @Override
    public BigDecimal calculateCosts(String contractNumber) {
        log.info("Calculating costs for investmentrules handler");
        return BigDecimal.valueOf(30);
    }

    @Override
    public void deleteOperation(String contractNumber) {
        log.info("Deleting operation to investmentrules handler");
    }
}
