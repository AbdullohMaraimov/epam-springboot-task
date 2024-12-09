package com.gym.crm.controller;

import com.gym.crm.controller.documentation.TrainingControllerDocumentation;
import com.gym.crm.model.dto.request.TrainingRequest;
import com.gym.crm.model.dto.response.ApiResponse;
import com.gym.crm.model.dto.response.TrainingResponse;
import com.gym.crm.model.entity.TrainingType;
import com.gym.crm.service.TrainingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/training")
@RequiredArgsConstructor
public class TrainingController implements TrainingControllerDocumentation {

    private final TrainingService trainingService;
    private final String TRAINING_SERVICE = "trainingService";

    @PostMapping
    @CircuitBreaker(name = TRAINING_SERVICE, fallbackMethod = "createTrainingFallback")
    public ApiResponse<Void> create(@Valid @RequestBody TrainingRequest request, @RequestHeader("Authorization") String authorization) {
        log.info("Creating training: {}", request);
        trainingService.create(request, authorization);
        log.info("Created training: {}", request);
        return new ApiResponse<>(200,true, null, "Training created successfully!");
    }

    public ApiResponse<Void> createTrainingFallback(TrainingRequest request, String authorization, Throwable throwable) {
        log.error("Fallback triggered due to: {}", throwable.getMessage());
        return new ApiResponse<>(500,true, null, "Training service is temporarily unavailable. Please try again later!");
    }

    @GetMapping("{id}")
    public ApiResponse<TrainingResponse> findById(@PathVariable Long id) {
        log.info("Finding training with ID {}", id);
        TrainingResponse response = trainingService.findById(id);
        return new ApiResponse<>(200, true, response, "Successfully found");
    }

    @GetMapping
    public ApiResponse<List<TrainingResponse>> findAll() {
        log.info("Finding all trainings");
        List<TrainingResponse> responses = trainingService.findAll();
        return new ApiResponse<>(200,true, responses, "Success!");
    }

    @GetMapping("/type")
    public ApiResponse<List<TrainingType>> findAllTrainingTypes() {
        log.info("Finding all training types");
        List<TrainingType> trainingTypes = trainingService.findAllTrainingTypes();
        return new ApiResponse<>(200, true, trainingTypes, "Successfully found!");
    }

}
