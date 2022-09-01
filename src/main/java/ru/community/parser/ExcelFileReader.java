package ru.community.parser;



import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;
import ru.community.converter.excel.ExcelConverter;


import java.io.IOException;
import java.util.List;

@Log4j2
public class ExcelFileReader extends FileParser {

    @Override

    public <T> List<T> read(Class<T> className, MultipartFile file) throws IOException {
        return  ExcelConverter.excelConverter(file, className);
    }
}
