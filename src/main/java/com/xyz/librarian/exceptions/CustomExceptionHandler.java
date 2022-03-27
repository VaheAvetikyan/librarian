package com.xyz.librarian.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger LOGGER = LogManager.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> errorThrown(Exception exception) {
        Map<String, String> errorMessage = new LinkedHashMap<>();
        errorMessage.put("message", exception.getMessage());
        errorMessage.put("details", "Something went wrong");
        LOGGER.error("Error caught: {}", exception.getMessage());
        return ResponseEntity.internalServerError().body(errorMessage);
    }
}
