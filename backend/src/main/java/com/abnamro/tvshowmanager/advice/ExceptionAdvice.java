package com.abnamro.tvshowmanager.advice;

import com.abnamro.tvshowmanager.common.dto.Response;
import com.abnamro.tvshowmanager.common.exceptions.BadRequest;
import com.abnamro.tvshowmanager.common.exceptions.ResourceNotFound;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.info(ex.getMessage());
        var response = Response.failedResponse(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({BadRequest.class, ResourceNotFound.class})
    public ResponseEntity<Response<Object>> handleClientExceptions(RuntimeException ex) {
        log.info(ex.getMessage());
        HttpStatus status = retrieveResponseStatusForClientErrors(ex);

        var response = Response.failedResponse(ex.getMessage());

        return ResponseEntity.status(status).body(response);
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Response<Object>> handleResourceNotFoundException(NoResourceFoundException ex) {
        log.info(ex.getMessage());
        var response = Response.failedResponse("The requested resource was not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<Response<Object>> handleRateLimiterException(RequestNotPermitted ex) {
        log.info(ex.getMessage());
        var response = Response.failedResponse("Too many requests. Please try again later.");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Response<Object>> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.info(ex.getMessage());
        var response = Response.failedResponse("The requested method is not allowed for the resource.");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleServerExceptions(Exception ex) {
        log.error("{}: {}", ex.getClass(), ex.getMessage());
        var response = Response.failedResponse("An unexpected error occurred while processing your request. Please try again later.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    private HttpStatus retrieveResponseStatusForClientErrors(RuntimeException ex) {
        ResponseStatus annotation = ex.getClass().getAnnotation(ResponseStatus.class);
        return annotation != null ? annotation.value() : HttpStatus.BAD_REQUEST;
    }
}
