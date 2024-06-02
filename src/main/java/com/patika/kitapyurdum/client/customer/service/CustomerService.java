package com.patika.kitapyurdum.client.customer.service;

import com.patika.kitapyurdum.client.customer.CustomerClient;
import com.patika.kitapyurdum.client.customer.dto.response.CustomerResponse;
import com.patika.kitapyurdum.dto.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerService {

    private final CustomerClient customerClient;

    public CustomerResponse getCustomerById(Long customerId) {

        GenericResponse<CustomerResponse> response = customerClient.getById(customerId);

        if (response.getError() != null) {
            log.error("customer bulunamadı!");
        }

        return response.getData();
    }
}
