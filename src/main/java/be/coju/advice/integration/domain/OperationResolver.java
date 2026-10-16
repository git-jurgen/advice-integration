package be.coju.advice.integration.domain;

import be.coju.advice.integration.web.AdviceRequest;
import be.coju.advice.integration.web.OperationType;

public interface OperationResolver {
    boolean supports(OperationType operationType);

    default OperationHandler resolve(AdviceRequest adviceRequest) {
        return resolve(adviceRequest, WorkflowContext.SIMULATION);
    }

    default OperationHandler resolve(AdviceRequest adviceRequest, WorkflowContext workflowContext) {
        return resolve(adviceRequest);
    }
}

