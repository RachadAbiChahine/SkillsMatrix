package com.matrix_skills.team.errors;

public class TeamNotFoundException extends Exception {

    public TeamNotFoundException() {
        super("Team not found");
    }

    public TeamNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamNotFoundException(String message) {
        super(message);
    }
}
