package com.dozie.customer.payload;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email
) {
}
