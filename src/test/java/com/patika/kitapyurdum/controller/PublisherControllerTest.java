package com.patika.kitapyurdum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.patika.kitapyurdum.dto.request.PublisherSaveRequest;
import com.patika.kitapyurdum.service.PublisherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PublisherController.class)
class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublisherService publisherService;

    @Test
    void save_successfully() throws Exception {

        //given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String body = objectMapper.writeValueAsString(preparePublisherSaveRequest());

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/publishers")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        //then - assertion - doğrulama
        resultActions
                .andExpect(status().isCreated());

        verify(publisherService,times(1)).save(Mockito.any(PublisherSaveRequest.class));
    }

    private PublisherSaveRequest preparePublisherSaveRequest() {
        PublisherSaveRequest request = new PublisherSaveRequest();
        request.setName("can yayınları");
        request.setCreatedDate(LocalDate.now());
        return request;
    }
}