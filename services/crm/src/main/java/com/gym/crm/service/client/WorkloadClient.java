package com.gym.crm.service.client;

import com.gym.crm.model.dto.request.TrainerWorkload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "trainer-hours-service"
)
public interface WorkloadClient {

    @PostMapping("/api/v1/workload")
    ResponseEntity<?> processWorkload(@RequestBody TrainerWorkload workload, @RequestHeader("Authorization") String authorization);

}
