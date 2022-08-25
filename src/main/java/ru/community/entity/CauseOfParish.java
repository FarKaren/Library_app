package ru.community.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CauseOfParish {

    ARRIVAL("поступление"),
    TRANSFER("перемещение"),
    WRITE_OFF("списание");

    private String description;

    CauseOfParish(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
