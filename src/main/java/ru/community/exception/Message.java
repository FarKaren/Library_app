package ru.community.exception;

public enum Message {

    READER_NOT_FOUND("Reader not found"),
    LIBRARIAN_NOT_FOUND("Librarian not found"),
    INCORRECT_REQUEST("Invalid request"),
    LIBRARIAN_DEPARTMENT_NOT_FOUND("LibrarianDepartment not found"),
    BOOK_TRANSFER_NOT_FOUND("BookTransfer not found"),
    BOOK_NOT_FOUND("Book not found");

    public String description;


    Message(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
