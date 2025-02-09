package com.gym.crm.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiResponse<T> (
        int statusCode,
        boolean success,
        T data,
        String message
) {

    public ApiResponse(int statusCode, String message, boolean status) {
        this(statusCode, status, null, message);
    }
}
