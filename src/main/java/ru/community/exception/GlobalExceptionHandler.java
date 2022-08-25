package ru.community.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ElementException> handleException(LibrarianNotFound ex) {
        ElementException response = new ElementException();
        response.setMessage(Message.LIBRARIAN_NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ElementException> handleException(ReaderNotFound ex) {
        ElementException exception = new ElementException();
        exception.setMessage(Message.READER_NOT_FOUND);
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ElementException> handleException(Exception ex){
        ElementException exception = new ElementException();
        exception.setMessage(Message.INCORRECT_REQUEST);
        log.error(ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ElementException> handleException(LibrarianDepartmentNotFound ex) {
        ElementException exception = new ElementException();
        exception.setMessage(Message.LIBRARIAN_DEPARTMENT_NOT_FOUND);
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ElementException> handleException(BookTransferNotFound ex) {
        ElementException exception = new ElementException();
        exception.setMessage(Message.BOOK_TRANSFER_NOT_FOUND);
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ElementException> handleException(BookNotFound ex) {
        ElementException exception = new ElementException();
        exception.setMessage(Message.BOOK_NOT_FOUND);
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
}

