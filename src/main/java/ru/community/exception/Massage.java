package ru.community.exception;

public enum Massage {
    READER_NOT_FOUND("Reader not found"), INCORRECT_REQUEST("Invalid request");

    public String description;

    Massage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
