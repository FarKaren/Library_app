package ru.community.exception;


import lombok.Setter;

@Setter
public class ElementException {
    private Massage massage;

    public String getMassage() {
        return massage.getDescription();
    }
}
