package com.patika.kitapyurdum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KitapyurdumServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KitapyurdumServiceApplication.class, args);
    }

/*
    @Bean
    public CustomerController customerController() {
        return new CustomerController(customerService());
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService(customerRepository());
    }

    @Bean
    public CustomerRepository customerRepository() {
        return new CustomerRepository();
    }
*/
}
