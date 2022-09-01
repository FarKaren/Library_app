package ru.community.converter.csv;

import com.opencsv.bean.AbstractBeanField;
import ru.community.entity.Genre;

public class GenreCsvConverter extends AbstractBeanField {
    @Override
    protected Object convert(String s) {
        Genre[] values = Genre.values();
        for (Genre value : values) {
            if (value.getDescription().equals(s)) {
                return value;
            }
        }
        return "Return null";
    }
}
