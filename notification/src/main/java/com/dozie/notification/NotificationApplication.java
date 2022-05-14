package com.dozie.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"com.dozie.notification",
                "com.dozie.amqp",
        }
)
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }
//
//    @Bean
//    CommandLineRunner commandLineRunner(
//            RabbitMqMessageProducer producer,
//            NotificationConfig notificationConfig
//            ){
//        return args -> {
//            producer.publish(
//                    new Person("Alibaba", 34),
//                    notificationConfig.getInternalExchange(),
//                    notificationConfig.getInternalNotificationRoutingKey());
//        };
//    }
//
//    record Person(String name, int age){}
}
