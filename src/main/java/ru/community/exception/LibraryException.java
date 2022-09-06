package ru.community.exception;

import lombok.Getter;

@Getter
public class LibraryException extends RuntimeException{

    private final Message messageType;

    public LibraryException(Message message) {
        super(message.getDescription());
        this.messageType = message;
    }
}
