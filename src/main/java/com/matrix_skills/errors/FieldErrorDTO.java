package com.matrix_skills.errors;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FieldErrorDTO {

    private final String field;
    private final String message;



}
