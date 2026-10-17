package be.coju.advice.integration.domain;

import be.coju.advice.integration.web.AdviceRequest;
import be.coju.advice.integration.web.Operation;
import be.coju.advice.integration.web.OperationType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

class PaymentResolverTest {
    private final InvestmentRulesHandler investmentRulesHandler = new InvestmentRulesHandler();
    private final RequestScheduledPaymentHandler requestScheduledPaymentHandler = new RequestScheduledPaymentHandler();
    private final UpdateScheduledPaymentHandler updateScheduledPaymentHandler = new UpdateScheduledPaymentHandler();
    private final PaymentResolver resolver = new PaymentResolver(
            investmentRulesHandler,
            requestScheduledPaymentHandler,
            updateScheduledPaymentHandler
    );

    @Test
    void resolveSimulationUsesInvestmentRulesHandlerForZeroAmount() {
        AdviceRequest request = createRequest(OperationType.INVESTMENT_RULES, BigDecimal.ZERO, true);

        assertSame(investmentRulesHandler, resolver.resolve(request, WorkflowContext.SIMULATION));
    }

    @Test
    void resolveConfirmationUsesInvestmentRulesHandlerForZeroAmount() {
        AdviceRequest request = createRequest(OperationType.INVESTMENT_RULES, BigDecimal.ZERO, true);

        assertSame(investmentRulesHandler, resolver.resolve(request, WorkflowContext.CONFIRMATION));
    }

    @Test
    void resolveSimulationUsesUpdateScheduledPaymentHandlerWhenPaymentExists() {
        AdviceRequest request = createRequest(OperationType.PAYMENT_PLAN, BigDecimal.TEN, true);

        assertSame(updateScheduledPaymentHandler, resolver.resolve(request, WorkflowContext.SIMULATION));
    }

    @Test
    void resolveConfirmationUsesUpdateScheduledPaymentHandlerWhenPaymentExists() {
        AdviceRequest request = createRequest(OperationType.PAYMENT_PLAN, BigDecimal.TEN, true);

        assertSame(updateScheduledPaymentHandler, resolver.resolve(request, WorkflowContext.CONFIRMATION));
    }

    @Test
    void resolveSimulationUsesRequestScheduledPaymentHandlerWhenNoPaymentExists() {
        AdviceRequest request = createRequest(OperationType.PAYMENT_PLAN, BigDecimal.TEN, false);

        assertSame(requestScheduledPaymentHandler, resolver.resolve(request, WorkflowContext.SIMULATION));
    }

    @Test
    void resolveConfirmationUsesRequestScheduledPaymentHandlerWhenNoPaymentExists() {
        AdviceRequest request = createRequest(OperationType.PAYMENT_PLAN, BigDecimal.TEN, false);

        assertSame(requestScheduledPaymentHandler, resolver.resolve(request, WorkflowContext.CONFIRMATION));
    }

    private AdviceRequest createRequest(OperationType operationType, BigDecimal amount, boolean hasScheduledPayment) {
        return AdviceRequest.builder()
                .contractNumber("contract-1")
                .hasScheduledPayment(hasScheduledPayment)
                .operations(List.of(Operation.builder()
                        .operationType(operationType)
                        .amount(amount)
                        .build()))
                .build();
    }
}
