package ru.community.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    ACTUAL("НА РУКАХ"),
    RETURNED("ВОЗВРАЩЕНО"),
    EXPIRED("ПРОСРОЧЕНО");

    private String description;

    Status(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
