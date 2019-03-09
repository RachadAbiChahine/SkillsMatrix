package com.matrix_skills.team.errors;


public class TeamAlreadyExistException extends RuntimeException {
    public TeamAlreadyExistException() {
        super("Team already Exist");
    }

    public TeamAlreadyExistException(String message, Exception e) {
        super(message, e);
    }

    public TeamAlreadyExistException(String message) {
        super(message);
    }

}
