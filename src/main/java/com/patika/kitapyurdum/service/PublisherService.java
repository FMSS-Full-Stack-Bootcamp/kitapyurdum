package com.patika.kitapyurdum.service;

import com.patika.kitapyurdum.converter.PublisherConverter;
import com.patika.kitapyurdum.dto.request.PublisherSaveRequest;
import com.patika.kitapyurdum.model.Publisher;
import com.patika.kitapyurdum.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class PublisherService {

    private final PublisherRepository publisherRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(PublisherSaveRequest request) {

        Publisher publisher = PublisherConverter.toPublisher(request);

        Publisher savedPublisher = publisherRepository.save(publisher);

        log.info("publisher saved. {}", savedPublisher.getId());
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Optional<Publisher> getByName(String publisherName) {
        return getAllPublishers().stream()
                .filter(publisher -> publisher.getName().equals(publisherName))
                .findFirst();
    }

    public Optional<Publisher> getById(Long publisherId) {
        return publisherRepository.findById(publisherId);
    }

}
