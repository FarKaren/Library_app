package ru.community.converter.excel;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;
import ru.community.entity.Genre;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class ExcelConverter {

    private static final String EXCEPTION_OCCURRED = "Exception occurred: ";
    private static final String dd_MM_YYYY = "dd/MM/yyyy";

    public static <T> List<T> excelConverter(MultipartFile file, Class<T> className) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file.getInputStream());
        } catch (IOException e) {
            log.debug(EXCEPTION_OCCURRED + e.getMessage());
        }
        assert workbook != null;
        Sheet worksheet = workbook.getSheetAt(0);
        Row headerRow = worksheet.getRow(worksheet.getFirstRowNum());

        List<T> list = new ArrayList<T>();
        for (Row row : worksheet) {
            if (headerRow != row) {
                list.add(className.cast(excelRowMapper(row, headerRow, className)));
            }
        }
        return list;
    }

    private static <T> Object excelRowMapper(Row currentRow, Row headerRow, Class<T> className) {
        Map<String, String> excelRowMap = new HashMap<>();
        for (Cell cell : currentRow) {
            excelRowMap.put(getExcelCellValue(headerRow.getCell(cell.getColumnIndex())), getExcelCellValue(cell));
        }

        Object object = null;

        String author = excelRowMap.get("Author");
        String title =  excelRowMap.get("Title");
        int publisherYear = Integer.parseInt(excelRowMap.get("PublisherYear"));
        String publisher = excelRowMap.get("Publisher");
        int countOfPage = Integer.parseInt(excelRowMap.get("CountOfPage"));
        Genre genre = null;
        for (Genre g : Genre.values()) {
            if(g.getDescription().equals(excelRowMap.get("Genre")))
                genre = g;
        }

        try {
            object = className.getConstructor(String.class, String.class, int.class, Genre.class, String.class, int.class)
                    .newInstance(author, title, publisherYear, genre, publisher, countOfPage);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            log.debug(EXCEPTION_OCCURRED + e.getMessage());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return object;
    }

    private static String getExcelCellValue(Cell cell) {
        DataFormatter df = new DataFormatter();
        switch (cell.getCellType()) {
            case  STRING:
                return cell.getRichStringCellValue().getString();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat(dd_MM_YYYY).format(cell.getDateCellValue());
                } else {
                    String num = df.formatCellValue(cell);
                    return !num.contains(".") ? num : num.replaceAll("0*$", "").replaceAll("\\.$", "");
                }
            case BOOLEAN:
                return "" + cell.getBooleanCellValue();
            case  FORMULA:
                return cell.getCellFormula();
            case  ERROR:
                return Byte.valueOf(cell.getErrorCellValue()).toString();
            default:
                return "";
        }
    }


}