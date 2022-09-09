package ru.community.parser;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.community.exception.FileReaderException;

@Component
public final class ParserFactory {


    public <T> FileReader createParser(MultipartFile file) throws FileReaderException {
        final var fileName = file.getOriginalFilename();
        if (fileName.toLowerCase().endsWith(".csv")) {
            return new CsvFileReader();
        }
        if (fileName.toLowerCase().endsWith(".xls") || fileName.toLowerCase().endsWith(".xlsx")) {
            return new ExcelFileReader();
        }
        throw new FileReaderException("Cannot find file reader for file = " + fileName);

    }
}

