package ru.community.converter.json;


import ru.community.entity.ReasonOfParish;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ReasonOfParishJsonConverter implements AttributeConverter<ReasonOfParish, String> {

    @Override
    public String convertToDatabaseColumn(ReasonOfParish g) {
        if (g == null) {
            return null;
        }
        return g.getDescription();
    }

    @Override
    public ReasonOfParish convertToEntityAttribute(String description) {
        if (description == null) {
            return null;
        }

        return Stream.of(ReasonOfParish.values())
                .filter(c -> c.getDescription().equals(description))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
