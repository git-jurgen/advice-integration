package be.coju.advice.integration.web;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class Cost {
    private String costType;
    private BigDecimal amount;
}
