package com.example.bootcamp.util;

import com.example.bootcamp.exception.ResourceNotUploadedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {
    public static final String PATH = "src/main/resources/static/images/";
    public static String getFileName(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(PATH + file.getOriginalFilename());
            Files.write(path, bytes);

            return file.getOriginalFilename();
        } catch (Exception e) {
            throw new ResourceNotUploadedException("Resource Not Uploaded: " + e);
        }
    }

}
