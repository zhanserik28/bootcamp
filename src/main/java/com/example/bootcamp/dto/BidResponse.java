package com.example.bootcamp.dto;

import lombok.Data;

@Data
public class BidResponse {
    private boolean success;
    private String message;


    public BidResponse(boolean b, String s) {
        success = b;
        message = s;
    }
}