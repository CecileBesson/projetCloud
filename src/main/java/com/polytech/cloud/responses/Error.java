package com.polytech.cloud.responses;

import org.springframework.http.HttpStatus;

public class Error extends ApiResponse {
    /**
     * Creates an API Response.
     *
     * @param _status  the status code (http)
     * @param _message the message (response)
     */
    public Error(HttpStatus _status, String _message) {
        super(_status, _message);
    }
}
