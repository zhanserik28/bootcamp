package com.example.bootcamp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceNotUploadedException extends RuntimeException {
    public ResourceNotUploadedException(String message) {
        super(message);
    }
}