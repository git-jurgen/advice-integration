package be.coju.advice.integration.domain;

import be.coju.advice.integration.web.AdviceRequest;
import be.coju.advice.integration.web.OperationType;

public interface OperationResolver {
    boolean supports(OperationType operationType);
    OperationHandler resolve(AdviceRequest adviceRequest);
}

