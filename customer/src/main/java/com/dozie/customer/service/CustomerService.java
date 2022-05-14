package com.dozie.customer.service;

import com.dozie.amqp.RabbitMqMessageProducer;
import com.dozie.client.model.fraud.FraudClient;
import com.dozie.client.model.notification.NotificationRequest;
import com.dozie.client.payload.FraudCheckResponse;
import com.dozie.customer.model.Customer;
import com.dozie.customer.payload.CustomerRegistrationRequest;
import com.dozie.customer.repo.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
//    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
//    private final NotificationClient notificationClient;
    private final RabbitMqMessageProducer producer;

    public void registerCustomer(CustomerRegistrationRequest customerRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();
        customerRepository.saveAndFlush(customer);
//         = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId());
        FraudCheckResponse response = fraudClient.isFraudster(customer.getId());
//        if (response != null) {
            if (response.isFraudster()) {
                throw new IllegalStateException("this is a fraud");
            }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, Welcome to dozie world...", customer.getFirstName())
        );
        producer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );

        // todo: validate email,
        //  todo: check if fraudster,
        //   todo: send notification,
        //  todo: check if email is taken,
    }
}
