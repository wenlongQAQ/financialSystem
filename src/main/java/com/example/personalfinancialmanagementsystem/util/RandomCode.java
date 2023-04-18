package com.example.personalfinancialmanagementsystem.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomCode {
    public static final Map<String,String> codeMap = new HashMap<>();
    public static String generateCode(){
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6;i++){
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }
}
