package ru.community.exception;


import lombok.Setter;

@Setter
public class ElementException {
    private Message message;

    public String getMessage() {
        return message.getDescription();
    }
}
