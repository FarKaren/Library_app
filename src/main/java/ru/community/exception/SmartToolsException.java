package ru.community.exception;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class SmartToolsException extends RuntimeException{

    @Autowired
    private final Message message;

    public SmartToolsException(Message message) {
        super(message.getDescription());
        this.message = message;
    }
}
