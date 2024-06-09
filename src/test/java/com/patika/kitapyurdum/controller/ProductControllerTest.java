package com.patika.kitapyurdum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patika.kitapyurdum.dto.request.ProductSaveRequest;
import com.patika.kitapyurdum.dto.request.ProductSearchRequest;
import com.patika.kitapyurdum.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void save_successfully() throws Exception {

        //given
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(prepareProductSaveRequest());

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/products")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        //then - assertion
        resultActions.andExpect(status().isCreated());
        verify(productService, times(1)).save(Mockito.any(ProductSaveRequest.class));
    }

    private ProductSaveRequest prepareProductSaveRequest() {
        ProductSaveRequest request = new ProductSaveRequest();
        request.setName("product1");
        request.setDescription("desc");
        request.setAmount(BigDecimal.ONE);
        request.setPublisherId(1L);
        return request;
    }

    @Test
    void search_successfully() throws Exception {

        //given
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(prepareProductSearchRequest());

        //when - then
        mockMvc.perform(get("/api/v1/products")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService, times(1)).getAll(Mockito.any(ProductSearchRequest.class));
    }

    private ProductSearchRequest prepareProductSearchRequest() {
        ProductSearchRequest productSearchRequest = new ProductSearchRequest();
        productSearchRequest.setPage(0);
        productSearchRequest.setSize(0);
        productSearchRequest.setName("product1");
        productSearchRequest.setAmount(BigDecimal.ONE);
        productSearchRequest.setPublisherName("publisherName");
        return productSearchRequest;
    }
}