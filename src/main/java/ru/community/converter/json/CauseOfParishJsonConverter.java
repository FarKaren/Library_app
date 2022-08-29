package ru.community.converter.json;


import ru.community.entity.CauseOfParish;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CauseOfParishJsonConverter implements AttributeConverter<CauseOfParish, String> {

    @Override
    public String convertToDatabaseColumn(CauseOfParish g) {
        if (g == null) {
            return null;
        }
        return g.getDescription();
    }

    @Override
    public CauseOfParish convertToEntityAttribute(String description) {
        if (description == null) {
            return null;
        }

        return Stream.of(CauseOfParish.values())
                .filter(c -> c.getDescription().equals(description))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
