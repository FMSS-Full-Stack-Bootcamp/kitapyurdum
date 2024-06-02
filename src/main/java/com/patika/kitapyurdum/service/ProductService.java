package com.patika.kitapyurdum.service;

import com.patika.kitapyurdum.converter.ProductConverter;
import com.patika.kitapyurdum.dto.request.ProductSaveRequest;
import com.patika.kitapyurdum.dto.response.ProductResponse;
import com.patika.kitapyurdum.model.Product;
import com.patika.kitapyurdum.model.Publisher;
import com.patika.kitapyurdum.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final PublisherService publisherService;

    @CacheEvict(cacheNames = "products", allEntries = true)
    public void save(ProductSaveRequest request) {

        Optional<Publisher> optionalPublisher = publisherService.getByName(request.getPublisherName());

        if (optionalPublisher.isEmpty()) {
            log.error("publisher bulamadım : {}", request.getPublisherName());
            throw new RuntimeException("publisher bulamadım");
        }

        Product product = ProductConverter.toProduct(request, optionalPublisher.get());

        productRepository.save(product);

        log.info("product created : {}", product.toString());
    }

    @Cacheable(value = "products", cacheNames = "products")
    public Set<ProductResponse> getAll() {
        Set<Product> products = productRepository.getAll();
        log.info("db'den getirildi. product size:{}", products.size());
        return ProductConverter.toResponse(products);
    }

    public List<Product> getByIdList(List<Long> productIdList) {
        return productRepository.getAll().stream().toList();
    }
}
