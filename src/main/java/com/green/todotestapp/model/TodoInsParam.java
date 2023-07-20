package com.green.todotestapp.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
//클라이언트에서 받을 때
public class TodoInsParam {
    private String ctnt;
    private MultipartFile pic;


}
