package com.matrix_skills.errors;



import java.io.Serializable;

public class FieldErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String field;

    private final String message;

    public FieldErrorDTO( String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}
