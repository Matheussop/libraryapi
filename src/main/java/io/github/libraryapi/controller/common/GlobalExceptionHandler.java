package io.github.libraryapi.controller.common;

import io.github.libraryapi.controller.dto.ResponseError;
import io.github.libraryapi.exception.DuplicatedRegisterException;
import io.github.libraryapi.exception.OperationNotAllowedException;
import org.springframework.http.HttpStatus;

import io.github.libraryapi.controller.dto.ErrorField;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors( );
        List<ErrorField> errorList = fieldErrors
                .stream()
                .map(fe -> new ErrorField(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation Error.",
                errorList);
    }

    @ExceptionHandler(DuplicatedRegisterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleDuplicatedRegisterException(DuplicatedRegisterException e) {
        return ResponseError.conflict(e.getMessage());
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleOperationNotAllowedException(
            OperationNotAllowedException e){
        return ResponseError.defaultResponse(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleGenericException(RuntimeException e) {
        return new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"An unexpected error occurred: " + e.getMessage(), List.of());
    }
}
