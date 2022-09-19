package ru.community.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Reason {

    ARRIVAL("ПОСТУПЛЕНИЕ"),
    TRANSFER("ПЕРЕМЕЩЕНИЕ"),
    WRITE_OFF("СПИСАНИЕ");

    private String description;

    Reason(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    public static boolean isReasonPresent(Reason reason) {
        for (Reason value : values()) {
            if (reason.equals(value)) return true;
        }
        return false;
    }
}
