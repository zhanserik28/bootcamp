package com.example.bootcamp.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class AdCreateDto {
    private String title;
    private String description;
    private double minimumPrice;

}
