package ru.community.mapper;

import org.mapstruct.Mapper;
import ru.community.dto.RateRequestDto;
import ru.community.entity.BookRating;

@Mapper(componentModel = "spring")
public interface BookRatingMapper {
    BookRating ReteRequestDtoToBookRating(RateRequestDto rateRequestDto);
}
