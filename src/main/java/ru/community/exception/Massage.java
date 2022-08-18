package ru.community.exception;

public enum Massage {

    NOT_FOUND("Librarian not found"), INCORRECT_REQUEST("Invalid request");

    private String description;

    Massage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
