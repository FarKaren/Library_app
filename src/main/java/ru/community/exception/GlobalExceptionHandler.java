package ru.community.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ElementException> handleException(ReaderNotFound ex){
        ElementException exception = new ElementException();
        exception.setMassage(Massage.READER_NOT_FOUND);
        return new ResponseEntity<ElementException>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ElementException> handleException(Exception ex){
        ElementException exception = new ElementException();
        exception.setMassage(Massage.INCORRECT_REQUEST);
        return new ResponseEntity<ElementException>(exception, HttpStatus.BAD_REQUEST);
    }
}
