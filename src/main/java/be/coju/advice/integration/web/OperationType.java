package be.coju.advice.integration.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OperationType {
    SWITCH("switch"),
    PARTIAL_SURRENDER("partialSurrender"),
    TOTAL_SURRENDER("totalSurrender"),
    UNSOLICITED_PAYMENT("unsolicitedPayment"),
    PAYMENT_PLAN("paymentPlan"),
    INVESTMENT_RULES("investmentRules"),
    PAYMENT_PLAN_AND_INVESTMENT_RULES("paymentplanAndInvestmentrules"),
    TOTAL_SURRENDER_END_IGP("totalSurrenderEndIgp"),
    PARTIAL_SURRENDER_END_IGP("partialSurrenderEndIgp");

    private final String label;

    OperationType(String label) { this.label = label; }

    public String getLabel() { return label; }

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
