package ru.community.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ReasonOfParish {

    ARRIVAL("ПОСТУПЛЕНИЕ"),
    TRANSFER("ПЕРЕМЕЩЕНИЕ"),
    WRITE_OFF("СПИСАНИЕ");

    private String description;

    ReasonOfParish(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
