package crm.trainerhoursservice.controller;

import crm.trainerhoursservice.model.dto.TrainerWorkload;
import crm.trainerhoursservice.model.WorkloadSummary;
import crm.trainerhoursservice.service.WorkloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/workload")
@RequiredArgsConstructor
public class WorkloadController {

    private final WorkloadService service;

    @PostMapping
    public ResponseEntity<?> processWorkload(@RequestBody TrainerWorkload workload) {
        log.info("Received request to process workload for trainer: {}", workload.username());
        try {
            service.handleWorkload(workload);
            log.info("Successfully processed workload for trainer: {}", workload.username());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error occurred while processing workload for trainer: {}", workload.username(), e);
            return ResponseEntity.status(500).body("Failed to process workload");
        }
    }

    @GetMapping("/summary/{trainerUsername}")
    public ResponseEntity<WorkloadSummary> summary(@PathVariable String trainerUsername) {
        log.info("Received request  workload summary for trainer: {}", trainerUsername);
        try {
            WorkloadSummary summarize = service.summarize(trainerUsername);
            if (summarize == null) {
                log.warn("No summary found for trainer: {}", trainerUsername);
                return ResponseEntity.notFound().build();
            }
            log.info("Returning summary for trainer: {}", trainerUsername);
            log.debug("Summary details: {}", summarize);
            return ResponseEntity.ok(summarize);
        } catch (Exception e) {
            log.error("Error occurred while fetching summary for trainer: {}", trainerUsername, e);
            return ResponseEntity.status(500).body(null);
        }
    }

}
