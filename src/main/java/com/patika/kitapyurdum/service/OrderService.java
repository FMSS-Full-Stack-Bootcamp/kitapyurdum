package com.patika.kitapyurdum.service;

import com.patika.kitapyurdum.client.customer.dto.response.CustomerResponse;
import com.patika.kitapyurdum.client.customer.service.CustomerService;
import com.patika.kitapyurdum.converter.OrderConverter;
import com.patika.kitapyurdum.dto.request.OrderSaveRequest;
import com.patika.kitapyurdum.model.Order;
import com.patika.kitapyurdum.model.Product;
import com.patika.kitapyurdum.producer.NotificationProducer;
import com.patika.kitapyurdum.producer.dto.NotificationDto;
import com.patika.kitapyurdum.producer.dto.ProductDto;
import com.patika.kitapyurdum.producer.dto.enums.NotificationType;
import com.patika.kitapyurdum.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final NotificationProducer notificationProducer;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void create(OrderSaveRequest request) {

        CustomerResponse customerResponse = customerService.getCustomerById(request.getCustomerId());

        List<Product> productList = productService.getByIdList(request.getProductIdList());

        Order order = OrderConverter.convert(request, customerResponse.getId(), productList);

        orderRepository.save(order);

        log.info("order saved. customer id:{} : order:{}", customerResponse.getId(), order.toString());

        notificationProducer.sendNotification(prepareNotificationDto(NotificationType.MAIL, productList));
    }

    private NotificationDto prepareNotificationDto(NotificationType type, List<Product> productList) {
        return NotificationDto.builder()
                .notificationType(type)
                .productDtoList(prepareProductDtoList(productList))
                .build();
    }

    private List<ProductDto> prepareProductDtoList(List<Product> productList) {
       return productList
               .stream()
               .map(this::prepareProductDto)
               .toList();
    }

    private ProductDto prepareProductDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .desc(product.getDescription())
                .build();
    }
}
