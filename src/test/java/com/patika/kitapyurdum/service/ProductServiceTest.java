package com.patika.kitapyurdum.service;

import com.patika.kitapyurdum.dto.request.ProductSaveRequest;
import com.patika.kitapyurdum.exception.KitapYurdumException;
import com.patika.kitapyurdum.model.Product;
import com.patika.kitapyurdum.model.Publisher;
import com.patika.kitapyurdum.repository.ProductRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PublisherService publisherService;

    @Test
    void save_successfully() {
        //given
        ProductSaveRequest request = Instancio.of(ProductSaveRequest.class)
                .set(field("publisherId"), 1L)
                .create();
        Optional<Publisher> optionalPublisher = Optional.of(Instancio.of(Publisher.class)
                .set(field("id"), 1L)
                .set(field("name"), "can yayınları")
                .create());

        Mockito.when(publisherService.getById(1L))
                .thenReturn(optionalPublisher);

        Mockito.when(productRepository.save(Mockito.any(Product.class)))
                .thenReturn(Instancio.create(Product.class));

        //when
        productService.save(request);

        //then

        verify(productRepository, times(1)).save(Mockito.any(Product.class));

        verify(publisherService, times(1)).getById(1L);
    }

    @Test
    void shouldThrowException_whenPublisherIsNotFound() {

        ProductSaveRequest request = Instancio.of(ProductSaveRequest.class).create();

        //when
        KitapYurdumException kitapYurdumException = Assertions.assertThrows(KitapYurdumException.class, () -> productService.save(request));

        assertThat(kitapYurdumException.getMessage()).isEqualTo("publisher bulunamadı");

        verifyNoInteractions(productRepository);

    }


}