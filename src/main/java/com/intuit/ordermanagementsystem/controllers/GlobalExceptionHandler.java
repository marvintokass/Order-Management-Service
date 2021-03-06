package com.intuit.ordermanagementsystem.controllers;

import com.intuit.ordermanagementsystem.exceptions.ResourceNotFoundException;
import com.intuit.ordermanagementsystem.models.response.ErrorPayload;
import com.intuit.ordermanagementsystem.models.response.HandlerResponsePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler({ResourceNotFoundException.class, IllegalArgumentException.class, HttpMessageConversionException.class})
    public ResponseEntity<HandlerResponsePayload> handleException(Exception ex) {
        logErrorDetails(ex);
        return getResponseEntity(ex.getMessage(), ex.getLocalizedMessage(), 400 , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HandlerResponsePayload> handleGenericException(Exception ex) {
        logErrorDetails(ex);
        return getResponseEntity(ex.getMessage(), ex.getLocalizedMessage(), 500, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<HandlerResponsePayload> getResponseEntity(String error, String errorDetail, int statusCode, HttpStatus status) {
        ErrorPayload errorPayload = new ErrorPayload(error, errorDetail);
        HandlerResponsePayload response = new HandlerResponsePayload();
        response.setStatus(statusCode);
        response.setTimestamp(LocalDateTime.now());
        response.setTitle("Oops! Something went wrong");
        response.setErrors(new ArrayList<>(Arrays.asList(errorPayload)));

        return new ResponseEntity<>(response, status);
    }

    private void logErrorDetails(Exception ex) {
        logger.error("error: \n", ex);
    }

}
