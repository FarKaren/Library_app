package ru.community.converter.json;

import ru.community.entity.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        return status == null ? null : status.getDescription();
    }

    @Override
    public Status convertToEntityAttribute(String s) {
        return Stream.of(Status.values())
                .filter(status -> status.getDescription().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
