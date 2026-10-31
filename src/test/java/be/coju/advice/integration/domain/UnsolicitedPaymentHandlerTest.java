package be.coju.advice.integration.domain;

import be.coju.advice.integration.web.OperationType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnsolicitedPaymentHandlerTest {
    private final UnsolicitedPaymentHandler handler = new UnsolicitedPaymentHandler();

    @Test
    void supportsUnsolicitedPayment() {
        assertTrue(handler.supports(OperationType.UNSOLICITED_PAYMENT));
    }

    @Test
    void doesNotSupportOtherOperationTypes() {
        assertFalse(handler.supports(OperationType.INVESTMENT_RULES));
    }

    @Test
    void calculatesCostsAsNinety() {
        assertEquals(new BigDecimal("90"), handler.calculateCosts("contract-1"));
    }
}
