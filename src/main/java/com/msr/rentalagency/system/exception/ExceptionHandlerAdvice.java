package com.msr.rentalagency.system.exception;

import com.msr.rentalagency.system.Result;
import com.msr.rentalagency.system.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleObjectNotFoundException(ObjectNotFoundException exception)
    {
        return new Result(false, StatusCode.NOT_FOUND,exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result handleValidationException(MethodArgumentNotValidException exception)
    {
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());
        errors.forEach(error->{
            String key = ((FieldError) error).getField();
            String val = error.getDefaultMessage();
            map.put(key,val);
        });
        return new Result(false,StatusCode.INVALID_ARGUMENTS,"Provided arguments are invalid, see data for details.",map);
    }
}
