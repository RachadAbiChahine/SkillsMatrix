package com.matrix_skills.partner.errors;

public class PartnerNotFoundException extends Exception {

    public PartnerNotFoundException() {
        super("Partner not Found");
    }

    public PartnerNotFoundException(String message) {
        super(message);
    }

    public PartnerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
