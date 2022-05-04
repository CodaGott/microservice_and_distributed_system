package com.dozie.client.model.notification;

public record NotificationRequest(
        Long toCustomerId,
        String customerName,
        String message
) {

}
