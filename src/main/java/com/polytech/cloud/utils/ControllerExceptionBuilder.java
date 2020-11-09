package com.polytech.cloud.utils;

import com.polytech.cloud.responses.ApiResponse;
import com.polytech.cloud.responses.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ControllerExceptionBuilder {
    public static ResponseEntity<ApiResponse> buildErrorResponseAndPrintStackTrace(HttpStatus status, String message, Exception ex) {

        ex.printStackTrace();
        return new ResponseEntity<ApiResponse>
        (
                new Error(status, message),
                status
        );
    }
}
