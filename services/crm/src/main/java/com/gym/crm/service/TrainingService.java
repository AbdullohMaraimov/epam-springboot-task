package com.gym.crm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gym.crm.model.dto.request.TrainingRequest;
import com.gym.crm.model.dto.request.TrainingTypeRequest;
import com.gym.crm.model.dto.response.TrainingResponse;
import com.gym.crm.model.entity.TrainingType;

import java.time.LocalDate;
import java.util.List;

public interface TrainingService {

    void create(TrainingRequest trainingRequest, String authorization) throws JsonProcessingException;

    TrainingResponse findById(Long id);

    List<TrainingResponse> findAll();

    List<TrainingResponse> findTraineeTrainings(String username, LocalDate fromDate, LocalDate toDate, String trainingName, Long trainingTypeId);

    List<TrainingResponse> getTrainingsByTrainer(String username, LocalDate fromDate, LocalDate toDate, String traineeName);

    List<TrainingType> findAllTrainingTypes();

    boolean isDBEmpty();
}
