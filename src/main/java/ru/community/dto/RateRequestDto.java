package ru.community.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RateRequestDto {
    private String review;
    private int rating;
}
