package com.matrix_skills.errors;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for transferring error message with a list of field errors.
 */
public class ErrorDTO implements Serializable {

  private static final long serialVersionUID = -8189902525596349524L;

  private final String message;
private LocalDateTime timeStamp = LocalDateTime.now();

  private final String description;



  private List<FieldErrorDTO> fieldErrors;

  public ErrorDTO(String message) {
    this(message, null);
  }

  public ErrorDTO(String message, String description) {
    this.message = message;
    this.description = description;
  }

  public ErrorDTO(String message, String description, List<FieldErrorDTO> fieldErrors) {
    this.message = message;
    this.description = description;
    this.fieldErrors = fieldErrors;
  }

  public void add( String field, String message) {
    if (fieldErrors == null) {
      fieldErrors = new ArrayList<>();
    }
    fieldErrors.add(new FieldErrorDTO(field, message));
  }

  public String getMessage() {
    return message;
  }

  public String getDescription() {
    return description;
  }

  public List<FieldErrorDTO> getFieldErrors() {
    return fieldErrors;
  }

  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(LocalDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }
}
