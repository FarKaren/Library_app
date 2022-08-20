package ru.community.emun;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Genre {
    DETECTIVE("детектив"), THRILLER("триллер"),    POEM("поэма"),
    DRAMA("драма"), COMEDY("комедия"), HORROR("ужасы"),
    NOVEL("роман"), ADVENTURE("приключение"), FANTASY("фэнтэзи");

    private String description;

    Genre(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
