package com.dozie.customer.service;

import com.dozie.customer.model.Customer;
import com.dozie.customer.payload.CustomerRegistrationRequest;
import com.dozie.customer.payload.FraudCheckResponse;
import com.dozie.customer.repo.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    public void registerCustomer(CustomerRegistrationRequest customerRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();
        customerRepository.saveAndFlush(customer);
        FraudCheckResponse response = restTemplate.getForObject(
                "http://localhost:7081/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId());
        if (response != null) {
            if (response.isFraudster()) {
                throw new IllegalStateException("this is a fraud");
            }
        }
        // todo: validate email,
        //  todo: check if fraudster,
        //   todo: send notification,
        //  todo: check if email is taken,
    }
}
