package be.coju.advice.integration.web;

import be.coju.advice.integration.application.decision.AmendmentCancellationWorkflow;
import be.coju.advice.integration.application.decision.AmendmentConfirmationWorkflow;
import be.coju.advice.integration.application.simulation.AmendmentSimulationWorkflow;
import be.coju.advice.integration.domain.OperationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"", "/api/advice"})
@RequiredArgsConstructor
public class AdviceController {
    private final AmendmentSimulationWorkflow amendmentSimulationWorkflow;
    private final AmendmentConfirmationWorkflow amendmentConfirmationWorkflow;
    private final AmendmentCancellationWorkflow amendmentCancellationWorkflow;

    @PostMapping("/simulate")
    public SimulationResponse simulate(@RequestBody AdviceRequest request) {
        return amendmentSimulationWorkflow.execute(request);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Map<String, String>> confirm(@RequestBody AdviceRequest request) {
        amendmentConfirmationWorkflow.execute(request);

        String url = "https://example.com/confirm/" + request.getContractNumber();
        return ResponseEntity.ok(Map.of("url", url));
    }

    @PostMapping("/cancel")
    public ResponseEntity<Map<String, String>> cancel(@RequestBody AdviceRequest request) {
        amendmentCancellationWorkflow.execute(request);

        return ResponseEntity.ok().build();
    }
}
