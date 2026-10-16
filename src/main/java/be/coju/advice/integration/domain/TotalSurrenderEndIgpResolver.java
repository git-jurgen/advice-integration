package be.coju.advice.integration.domain;

import be.coju.advice.integration.web.AdviceRequest;
import be.coju.advice.integration.web.OperationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TotalSurrenderEndIgpResolver implements OperationResolver {
    private final TotalSurrenderHandler totalSurrenderHandler;
    private final PartialSurrenderEndIgpHandler partialSurrenderEndIgpHandler;

    @Override
    public boolean supports(OperationType operationType) {
        return OperationType.TOTAL_SURRENDER_END_IGP.equals(operationType);
    }

    @Override
    public OperationHandler resolve(AdviceRequest adviceRequest) {
        if (hasAmountZero(adviceRequest)) {
            log.info("Handle total surrender end igp using total surrender handler");
            return totalSurrenderHandler;
        } else {
            log.info("Handle total surrender end igp using partial surrender end igp handler");
            return partialSurrenderEndIgpHandler;
        }
    }

    private boolean hasAmountZero(AdviceRequest adviceRequest) {
        return adviceRequest.getOperations().stream()
                .allMatch(op -> op.getAmount() != null && op.getAmount().compareTo(java.math.BigDecimal.ZERO) == 0);
    }
}
