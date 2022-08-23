package ru.community.parser;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MultipartToFile  {
    private static String upLoadFolderPath = "C:\\Users\\User\\IdeaProjects\\library-app\\src\\main\\resources\\Books.xlsx";

    public static File uploadToLocal(MultipartFile file){
        try {
            byte[] data = file.getBytes();
            Path path = Paths.get(upLoadFolderPath);
            Files.write(path, data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File(String.valueOf(Paths.get("C:\\Users\\User\\IdeaProjects\\library-app\\src\\main\\resources\\Books.xlsx")));
    }
}
