package ru.community.converter.json;


import ru.community.entity.Reason;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ReasonJsonConverter implements AttributeConverter<Reason, String> {

    @Override
    public String convertToDatabaseColumn(Reason g) {
        if (g == null) {
            return null;
        }
        return g.getDescription();
    }

    @Override
    public Reason convertToEntityAttribute(String description) {
        if (description == null) {
            return null;
        }

        return Stream.of(Reason.values())
                .filter(c -> c.getDescription().equals(description))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
