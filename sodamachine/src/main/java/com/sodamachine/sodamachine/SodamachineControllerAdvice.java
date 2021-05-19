package com.sodamachine.sodamachine;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class SodamachineControllerAdvice extends ResponseEntityExceptionHandler {

    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {


        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.toString());
        body.put("timestamp", new Date());
        body.put("errorMessage", errors);
        return new ResponseEntity<>(body, headers, status);

    }

    @ExceptionHandler(value = { SodaSoldOutException.class })
    public @ResponseBody ExceptionResponse handleInvalidInputException(SodaSoldOutException ex) {
        ExceptionResponse error = new ExceptionResponse();
        List<String> errorMessageinfo = new ArrayList<>();
        errorMessageinfo.add(ex.getMessage());
        error.setTimeStamp(new Date());
        error.setStatus(HttpStatus.BAD_REQUEST.toString());
        error.setErrorMessage(errorMessageinfo);

        return error;
    }

}
