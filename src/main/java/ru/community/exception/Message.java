package ru.community.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Message {

    READER_NOT_FOUND("Reader not found"),
    READER_ALREADY_EXISTS("Reader already exists"),
    LIBRARIAN_NOT_FOUND("Librarian not found"),
    LIBRARIAN_ALREADY_EXISTS("Librarian already exists"),
    BOOK_NOT_FOUND("Book not found"),
    DEPARTMENT_NOT_FOUND("Department not found"),
    DEPARTMENT_ALREADY_EXISTS("Department already exists"),
    INCORRECT_REQUEST("Invalid request"),
    REASON_NOT_FOUND("Reason of parish not found"),
    TECHNICAL_ERROR("Technical error"),
    BOOK_BINDING_NOT_FOUND("BookBinding not found"),
    LIBRARIAN_DEPARTMENT_NOT_FOUND("Связка библиотекаря и филиала не найдена"),
    BOOK_TRANSFER_NOT_FOUND("Перемещение книги не найдено");
    private String description;

    Message(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
