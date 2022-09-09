package ru.community.converter.json;

import ru.community.entity.Genre;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class GenreJsonConverter implements AttributeConverter<Genre, String> {

    @Override
    public String convertToDatabaseColumn(Genre g) {
        if (g == null) {
            return null;
        }
        return g.getDescription();
    }

    @Override
    public Genre convertToEntityAttribute(String description) {
        if (description == null) {
            return null;
        }

        return Stream.of(Genre.values())
                .filter(c -> c.getDescription().equals(description))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}