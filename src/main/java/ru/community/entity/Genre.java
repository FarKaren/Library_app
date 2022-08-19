package ru.community.entity;

public enum Genre {
    DETECTIVE("детектив"), THRILLER("триллер"), POEM("поэма"),
    DRAMA("драма"), COMEDY("комедия"), HORROR("ужасы"),
    SCIENCE("наука"), ADVENTURE("приключение"), FANTASY("фэнтэзи");

    private String description;

    Genre(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
