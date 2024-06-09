package com.patika.kitapyurdum.service;

import com.patika.kitapyurdum.dto.request.PublisherSaveRequest;
import com.patika.kitapyurdum.model.Publisher;
import com.patika.kitapyurdum.repository.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {

    @InjectMocks
    private PublisherService publisherService;

    @Mock
    private PublisherRepository publisherRepository;

    @Test
    void save() {

        //given
        Mockito.when(publisherRepository.save(Mockito.any(Publisher.class)))
                .thenReturn(preparePublisher());

        //when -> test edilecek methodunun çağrılması
        publisherService.save(preparePublisherSaveRequest());

        //then
        verify(publisherRepository, times(1)).save(Mockito.any(Publisher.class));
    }

    private Publisher preparePublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("publisher");
        publisher.setId(1L);
        publisher.setCreatedDate(LocalDate.now());
        publisher.setProducts(Set.of());
        return publisher;
    }

    private PublisherSaveRequest preparePublisherSaveRequest() {
        PublisherSaveRequest request = new PublisherSaveRequest();
        request.setName("publisher");
        request.setCreatedDate(LocalDate.now());
        return request;
    }

    @Test
    void getAllPublishers() {
    }

    @Test
    void getByName() {
    }

    @Test
    void getById() {
    }
}