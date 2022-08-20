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
    public ResponseEntity<ElementException> handleException(LibrarianNotFound ex){
        ElementException response = new ElementException();
        response.setMassage(Massage.LIBRARIAN_NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ElementException> handleException(Exception ex){

        ElementException response = new ElementException();
        response.setMassage(Massage.INCORRECT_REQUEST);
        log.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
