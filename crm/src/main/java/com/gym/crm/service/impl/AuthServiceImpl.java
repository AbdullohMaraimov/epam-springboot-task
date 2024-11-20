package com.gym.crm.service.impl;

import com.gym.crm.exception.CustomNotFoundException;
import com.gym.crm.model.dto.request.TraineeRequest;
import com.gym.crm.model.dto.request.TrainerRequest;
import com.gym.crm.model.dto.request.UserLoginRequest;
import com.gym.crm.model.dto.response.RegistrationResponse;
import com.gym.crm.model.entity.User;
import com.gym.crm.repository.UserRepository;
import com.gym.crm.service.AuthService;
import com.gym.crm.service.JwtService;
import com.gym.crm.service.TraineeService;
import com.gym.crm.service.TrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public String login(UserLoginRequest loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
        User user = userRepository.findByUsername(loginDto.username())
                .orElseThrow(() -> new CustomNotFoundException("User not found with username: %s".formatted(loginDto.username())));
        return jwtService.generateToken(user);
    }

    @Override
    public RegistrationResponse register(TraineeRequest registerDto) throws IOException {
        return traineeService.create(registerDto);
    }

    @Override
    public RegistrationResponse register(TrainerRequest registerDto) throws IOException {
        return trainerService.create(registerDto);
    }


}
