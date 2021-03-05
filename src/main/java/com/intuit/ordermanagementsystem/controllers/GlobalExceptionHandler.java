package com.intuit.ordermanagementsystem.controllers;
import com.intuit.ordermanagementsystem.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler({ResourceNotFoundException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleException(Exception ex) {
        logErrorDetails(ex);
        return getResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        logErrorDetails(ex);
        return getResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> getResponseEntity(String error, HttpStatus status) {
        return new ResponseEntity<>(error, status);
    }

    private void logErrorDetails(Exception ex) {
        logger.error("error: \n", ex);
    }

}
