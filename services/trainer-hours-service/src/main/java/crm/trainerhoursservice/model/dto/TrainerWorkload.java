package crm.trainerhoursservice.model.dto;

import crm.trainerhoursservice.model.constant.ActionType;

import java.io.Serializable;
import java.time.LocalDate;

public record TrainerWorkload(
        String username,
        String firstName,
        String lastName,
        boolean isActive,
        LocalDate trainingDate,
        double trainingDuration,
        ActionType actionType
) implements Serializable {}
