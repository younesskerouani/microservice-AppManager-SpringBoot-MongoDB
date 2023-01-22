package com.ixpath.appmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class ResEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ com.ixpath.appmanager.exception.Exception.class })
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex,
                                                           WebRequest request) {

        ErrorDetails error = new ErrorDetails(ex.getMessage(),ex.getCodeError());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}
