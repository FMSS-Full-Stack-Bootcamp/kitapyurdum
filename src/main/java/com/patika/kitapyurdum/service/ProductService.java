package com.patika.kitapyurdum.service;

import com.patika.kitapyurdum.converter.ProductConverter;
import com.patika.kitapyurdum.dto.request.ProductSaveRequest;
import com.patika.kitapyurdum.dto.request.ProductSearchRequest;
import com.patika.kitapyurdum.dto.response.ProductResponse;
import com.patika.kitapyurdum.exception.KitapYurdumException;
import com.patika.kitapyurdum.model.Product;
import com.patika.kitapyurdum.model.Publisher;
import com.patika.kitapyurdum.repository.ProductRepository;
import com.patika.kitapyurdum.repository.PublisherRepository;
import com.patika.kitapyurdum.repository.spesification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final PublisherRepository publisherRepository;

    //@CacheEvict(cacheNames = "products", allEntries = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = KitapYurdumException.class)
    public void save(ProductSaveRequest request) {
        log.info("Transaction 1: {}", TransactionAspectSupport.currentTransactionStatus().hashCode());
        Optional<Publisher> optionalPublisher = publisherRepository.findById(request.getPublisherId());

        if (optionalPublisher.isEmpty()) {
            log.error("publisher bulamadım : {}", request.getPublisherId());
            throw new KitapYurdumException("publisher bulunamadı");
        }

        Product savedProduct = savedProduct(request, optionalPublisher);

        log.info("product created : {}", savedProduct.getId());
    }

    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    private Product savedProduct(ProductSaveRequest request, Optional<Publisher> optionalPublisher) {
        log.info("Transaction 1: {}", TransactionAspectSupport.currentTransactionStatus().hashCode());
        Product product = ProductConverter.toProduct(request, optionalPublisher.get());
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    //@Cacheable(value = "products", cacheNames = "products")
    @Transactional(readOnly = true)
    public Set<ProductResponse> getAll(ProductSearchRequest request) {

        Specification<Product> productSpecification = ProductSpecification.initProductSpecification(request);

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.ASC, "amount"));

        Page<Product> products = productRepository.findAll(productSpecification, pageRequest);

        log.info("db'den getirildi. product size:{}", products.getSize());

        return ProductConverter.toResponse(products.stream().toList());
    }

    public List<Product> getByIdList(List<Long> productIdList) {
        return productRepository.findAll().stream().toList();
    }
}
