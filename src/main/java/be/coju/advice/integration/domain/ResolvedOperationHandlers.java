package be.coju.advice.integration.domain;

import be.coju.advice.integration.web.OperationType;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ResolvedOperationHandlers {
    private final Map<OperationType, OperationHandler> handlers;

    public ResolvedOperationHandlers(Map<OperationType, OperationHandler> handlers) {
        Map<OperationType, OperationHandler> sortedHandlers = new LinkedHashMap<>();
        handlers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(OperationType::getPriority)))
                .forEach(entry -> sortedHandlers.put(entry.getKey(), entry.getValue()));
        this.handlers = Collections.unmodifiableMap(sortedHandlers);
    }

    public Map<OperationType, OperationHandler> getHandlers() {
        return handlers;
    }

    public Map.Entry<OperationType, OperationHandler> getFirstEntry() {
        return handlers.entrySet().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No handlers found"));
    }

    public Map<OperationType, OperationHandler> getRemainingEntries() {
        Map<OperationType, OperationHandler> remainingHandlers = new LinkedHashMap<>(handlers);
        if (!remainingHandlers.isEmpty()) {
            remainingHandlers.remove(getFirstEntry().getKey());
        }
        return Collections.unmodifiableMap(remainingHandlers);
    }
}
