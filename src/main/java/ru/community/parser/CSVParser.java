//package ru.community.parser;
//
//
//import com.opencsv.CSVReader;
//import ru.community.entity.Book;
//import ru.community.entity.Genre;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class CSVParser {
//
//    public static List<Book> csvParser(String path){
//
//        List<Book> result = new ArrayList<>();
//
//       try(InputStream inputStream = new FileInputStream(path);
//           Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//           List<String[]> list = new CSVReader(reader).readAll();
//
//           for (String[] str : list) {
//               Book book = new Book();
//               book.setAuthor(str[0]);
//               book.setTitle(str[1]);
//               book.setPublisherYear(Integer.parseInt(str[2]));
//               for (Genre c : Genre.values())
//                   if(c.getDescription().equals(str[3]))
//                       book.setGenre(c);
//               book.setPublisher(str[4]);
//               book.setCountOfPage(Integer.parseInt(str[5]));
//               result.add(book);
//           }
//       } catch (IOException e) {
//           e.printStackTrace();
//       }
//
//        return result;
//    }
//}
