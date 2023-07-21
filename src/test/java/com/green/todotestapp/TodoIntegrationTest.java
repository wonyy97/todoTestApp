package com.green.todotestapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.green.todotestapp.model.TodoInsDto;
import com.green.todotestapp.model.TodoRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.annotation.Rollback;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TodoIntegrationTest extends IntergrationTest{

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Rollback(true)
    public void postTodo() throws Exception {

        String originalFileNm = "9f684ebc-0504-4561-bebd-e678476888c9.jpg";
        String contentType = "jpg";
        String filePath = "D:/download/shoppingmall/product/6/" + originalFileNm;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        MockMultipartFile pic = new MockMultipartFile("pic", originalFileNm, contentType, fileInputStream);

        mvc.perform(multipart("/api/todo")
                        .file(pic)
                        .part(new MockPart("ctnt", "테스트4".getBytes(StandardCharsets.UTF_8)))
                )
                .andExpect(status().isOk())
                .andDo(print());

        mvc.perform(multipart("/api/todo")
                        .file(pic)
                        .part(new MockPart("ctnt", "테스트5".getBytes(StandardCharsets.UTF_8)))
                )
                .andExpect(status().isOk())
                .andDo(print());

    }
}
