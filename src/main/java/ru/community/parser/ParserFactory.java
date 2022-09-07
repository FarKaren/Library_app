package ru.community.parser;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

@Component
public final class ParserFactory {


    public  <T> FileParser createParser(Class<T> clazz, MultipartFile file, String fileFormat) {

        switch (fileFormat.toLowerCase(Locale.ROOT)) {
            case "csv":
                return new CsvFileReader();
            case "excel":
                return new ExcelFileReader();
            default:
                throw new IllegalArgumentException("Incorrect file format");
        }
    }
}

