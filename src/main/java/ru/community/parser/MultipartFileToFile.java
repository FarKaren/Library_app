//package ru.community.parser;
//
//
//
//import org.springframework.web.multipart.MultipartFile;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.nio.charset.StandardCharsets;
//
//
//public class MultipartFileToFile {
//    private static String upLoadFolderPath = "books.csv";
//
//    public static String uploadToLocal(MultipartFile file){
//
//        try (FileOutputStream fos = new FileOutputStream(upLoadFolderPath);
//              OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
//              CSVWriter writer = new CSVWriter(osw)){
//
//              byte[] data = file.getBytes();
//              String decoded = new String(data, StandardCharsets.UTF_8);
//              String[] row = {decoded};
//
//              writer.writeNext(row);
//
//
//
////            Path path = Paths.get(upLoadFolderPath);
////            Files.write(path, data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return upLoadFolderPath;
//    }
//}
