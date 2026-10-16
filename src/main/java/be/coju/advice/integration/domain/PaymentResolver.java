package be.coju.advice.integration.domain;

import be.coju.advice.integration.web.AdviceRequest;
import be.coju.advice.integration.web.Operation;
import be.coju.advice.integration.web.OperationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentResolver implements OperationResolver {
    private final InvestmentRulesHandler investmentRulesHandler;
    private final RequestScheduledPaymentHandler requestScheduledPaymentHandler;
    private final UpdateScheduledPaymentHandler updateScheduledPaymentHandler;

    @Override
    public boolean supports(OperationType operationType) {
        return EnumSet.of(OperationType.PAYMENT_PLAN, OperationType.INVESTMENT_RULES, OperationType.PAYMENT_PLAN_AND_INVESTMENT_RULES).contains(operationType);
    }

    @Override
    public OperationHandler resolve(AdviceRequest adviceRequest) {
        OperationType operationType = adviceRequest.getOperationTypes().getFirst();
        log.info("Payment resolver resolve operation type: {}", operationType);

        if (isInvestmentRulesOperation(operationType) && isAmountZero(adviceRequest)) {
            return investmentRulesHandler;
        } else if (adviceRequest.hasScheduledPayment()) {
            return updateScheduledPaymentHandler;
        } else {
            return requestScheduledPaymentHandler;
        }
    }

    private boolean isInvestmentRulesOperation(OperationType operationType) {
        return operationType.equals(OperationType.INVESTMENT_RULES)
                || operationType.equals(OperationType.PAYMENT_PLAN_AND_INVESTMENT_RULES);
    }

    private boolean isAmountZero(AdviceRequest adviceRequest) {
        return adviceRequest.getOperations().stream()
                .anyMatch(op -> op.getAmount() != null && op.getAmount().compareTo(java.math.BigDecimal.ZERO) == 0);
    }
}
