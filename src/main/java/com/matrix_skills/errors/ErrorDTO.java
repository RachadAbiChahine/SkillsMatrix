package com.matrix_skills.errors;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for transferring error message with a list of field errors.
 *
 */
@AllArgsConstructor
@Getter
public class ErrorDTO {


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



  public void add( String field, String message) {
    if (fieldErrors == null) {
      fieldErrors = new ArrayList<>();
    }
    fieldErrors.add(new FieldErrorDTO(field, message));
  }


}
