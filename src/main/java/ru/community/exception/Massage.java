package ru.community.exception;

public enum Message {

    READER_NOT_FOUND("Reader not found"),
    LIBRARIAN_NOT_FOUND("Librarian not found"),
    INCORRECT_REQUEST("Invalid request");

    public String description;

    Message(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
