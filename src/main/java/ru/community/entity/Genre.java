package ru.community.entity;



import com.fasterxml.jackson.annotation.JsonValue;

public enum Genre {
    DETECTIVE("детектив"), THRILLER("триллер"), POEM("поэма"),
    DRAMA("драма"), COMEDY("комедия"), HORROR("ужасы"),
    NOVEL("роман"), ADVENTURE("приключение"), FANTASY("фэнтэзи");


    @JsonValue
    private String description;

    Genre(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
