package ru.community.parser;

import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

public final class ParserFactory {

    private static ParserFactory parserFactory;

    private ParserFactory() {
    }

    private static void parserInit() {
        parserFactory = new ParserFactory();
    }

    public static <T> FileParser createParser(Class<T> clazz, MultipartFile file, String fileFormat) {
        parserInit();
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

