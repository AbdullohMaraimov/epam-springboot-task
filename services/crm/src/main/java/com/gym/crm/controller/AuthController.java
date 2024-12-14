package com.gym.crm.controller;

import com.gym.crm.controller.documentation.AuthControllerDocumentation;
import com.gym.crm.model.dto.request.TraineeRequest;
import com.gym.crm.model.dto.request.TrainerRequest;
import com.gym.crm.model.dto.request.UserLoginRequest;
import com.gym.crm.model.dto.response.ApiResponse;
import com.gym.crm.model.dto.response.RegistrationResponse;
import com.gym.crm.service.TraineeService;
import com.gym.crm.service.TrainerService;
import com.gym.crm.service.client.AuthClient;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocumentation {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final AuthClient authClient;
    private final String AUTH_SERVICE_TRAINEE = "authServiceTrainee";
    private final String AUTH_SERVICE_TRAINER = "authServiceTrainer";
    private final String AUTH_SERVICE_LOGIN = "loginService";

    @PostMapping("/register-trainee")
    @CircuitBreaker(name = AUTH_SERVICE_TRAINEE, fallbackMethod = "registerTraineeFallback")
    public ApiResponse<RegistrationResponse> register(@RequestBody @Valid TraineeRequest dto) throws IOException {
        log.info("Registering trainee with the request : {}", dto);
        RegistrationResponse registrationResponse = traineeService.create(dto);
        return new ApiResponse<>(201, true, registrationResponse, "Saved successfully!");
    }

    public ApiResponse<RegistrationResponse> registerTraineeFallback(TraineeRequest dto, Throwable throwable) {
        log.error("Fallback triggered due to: {}", throwable.getMessage());
        return new ApiResponse<>(500,true, null, "Auth service is temporarily unavailable. Please try again later!");
    }

    @PostMapping("/register-trainer")
    @CircuitBreaker(name = AUTH_SERVICE_TRAINER, fallbackMethod = "registerTrainerFallback")
    public ApiResponse<RegistrationResponse> register(@RequestBody @Valid TrainerRequest dto) throws IOException {
        log.info("Registering trainer with the request : {}", dto);
        RegistrationResponse registrationResponse = trainerService.create(dto);
        return new ApiResponse<>(201, true,   registrationResponse, "Saved successfully!");
    }

    public ApiResponse<RegistrationResponse> registerTrainerFallback(TrainerRequest dto, Throwable throwable) {
        log.error("Fallback triggered due to: {}", throwable.getMessage());
        return new ApiResponse<>(500,true, null, "Auth service is temporarily unavailable. Please try again later!");
    }

    @GetMapping("/login")
    @CircuitBreaker(name = AUTH_SERVICE_LOGIN, fallbackMethod = "authServiceLogin")
    public ApiResponse<String> login(@Valid @RequestBody UserLoginRequest dto) {
        log.info("Logging in with username : {}", dto.username());
        ResponseEntity<String> response = authClient.login(dto);

        if (response.getStatusCode() == HttpStatus.LOCKED) {
            return new ApiResponse<> (401, false, null,"Invalid username or password");
        } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return new ApiResponse<> (423, false, null,"Too many failed login attempts. Please try again later.");
        }

        return new ApiResponse<>(200, true, response.getBody(), "OK");
    }

    public ApiResponse<RegistrationResponse> authServiceLogin(UserLoginRequest dto, Throwable throwable) {
        if (throwable instanceof FeignException.Unauthorized) {
            return new ApiResponse<>(401, false, null, "Invalid username or password");
        } else if (throwable instanceof FeignException.Forbidden) {
            return new ApiResponse<>(423, false, null, "User is locked for a while due to many failed attempts, please try again later");
        }

        log.error("Fallback triggered due to: {}", throwable.getMessage());
        log.error(throwable.getMessage());
        return new ApiResponse<>(500,true, null, "Auth service is temporarily unavailable. Please try again later!");
    }

}
