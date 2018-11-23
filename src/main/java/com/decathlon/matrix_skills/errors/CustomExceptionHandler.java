package com.decathlon.matrix_skills.errors;

import com.decathlon.matrix_skills.partner.errors.PartnerNotFoundException;
import com.decathlon.matrix_skills.team.errors.TeamAlreadyExistException;
import com.decathlon.matrix_skills.team.errors.TeamNotFoundException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> processRuntimeException(Exception ex) throws Exception {
        ResponseEntity.BodyBuilder builder;
        ErrorDTO errorDTO;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            errorDTO = new ErrorDTO("error." + responseStatus.value().value(), responseStatus.reason());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            errorDTO = new ErrorDTO("Internal server error", "Internal server error");
        }
        return builder.body(errorDTO);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ErrorDTO error = new ErrorDTO("BAD REQUEST", ex.getLocalizedMessage());

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.add(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return error;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorDTO error = new ErrorDTO("BAD REQUEST");
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
//TODO verification PathImpl
            error.add(((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().getName(), constraintViolation.getMessage());

        }
        return error;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        ErrorDTO errorDTO = new ErrorDTO("Http requestMethod not supported", ex.getLocalizedMessage());
        return errorDTO;
    }


    @ExceptionHandler(TeamAlreadyExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDTO handleTeamAlreadyExistException(TeamAlreadyExistException e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return errorDTO;
    }


    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorDTO handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), e.getLocalizedMessage());
        return errorDTO;
    }

    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleTeamNotFoundException(TeamNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return errorDTO;
    }

    @ExceptionHandler(PartnerNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handlePartnerNotFoundException(PartnerNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return errorDTO;
    }


}
