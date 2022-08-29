package ru.community.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CsvFileReader {

    public <T> List<T> read(Class<T> clazz, MultipartFile file) throws IOException {
        return new CsvToBeanBuilder<T>(new InputStreamReader(file.getInputStream()))
                .withType(clazz)
                .withIgnoreLeadingWhiteSpace(true)
                .build().parse();
    }

}
