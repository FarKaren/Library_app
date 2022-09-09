package ru.community.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleException(LibraryException ex) {
        log.error(ex.getMessageType());
        return new ResponseEntity<>(ex.getMessageType().getDescription(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            errors.put(fieldName, error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception ex){
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
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

