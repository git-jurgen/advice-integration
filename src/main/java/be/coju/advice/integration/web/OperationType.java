package be.coju.advice.integration.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OperationType {
    SWITCH("switch", 4),
    PARTIAL_SURRENDER("partialSurrender", 2),
    TOTAL_SURRENDER("totalSurrender", 3),
    UNSOLICITED_PAYMENT("unsolicitedPayment", 1),
    PAYMENT_PLAN("paymentPlan", 5),
    INVESTMENT_RULES("investmentRules", 6),
    PAYMENT_PLAN_AND_INVESTMENT_RULES("paymentplanAndInvestmentrules", 7),
    TOTAL_SURRENDER_END_IGP("totalSurrenderEndIgp", 8),
    PARTIAL_SURRENDER_END_IGP("partialSurrenderEndIgp", 9);

    private final String label;
    private final int priority;

    OperationType(String label, int priority) { this.label = label; this.priority = priority; }

    public String getLabel() { return label; }

    public int getPriority() {
        return priority;
    }

    @JsonValue
    @Override
    public String toString() { return label; }

    @JsonCreator
    public static OperationType fromLabel(String input) {
        if (input == null) throw new IllegalArgumentException("OperationType label must not be null");
        String normalized = normalize(input);
        for (OperationType t : values()) {
            if (normalize(t.label).equals(normalized) || normalize(t.name()).equals(normalized)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown OperationType: '" + input + "'");
    }

    private static String normalize(String s) {
        return s == null ? "" : s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
    }
}
