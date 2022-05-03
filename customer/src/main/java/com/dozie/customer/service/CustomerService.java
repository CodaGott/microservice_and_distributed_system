package com.dozie.customer.service;

import com.dozie.customer.model.Customer;
import com.dozie.customer.payload.CustomerRegistrationRequest;
import com.dozie.customer.repo.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void registerCustomer(CustomerRegistrationRequest customerRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();
        customerRepository.save(customer);
        // todo: validate email,
        //  todo: check if fraudster,
        //   todo: send notification,
        //  todo: check if email is taken,
    }
}
