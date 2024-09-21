package micro.qa.microkafka.handler;

import micro.qa.microkafka.event.PaymentCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    @KafkaListener(topics = "payment-created-event-topic", groupId = "payment-created-event")
    public void handle(PaymentCreatedEvent paymentCreatedEvent){
        System.out.println("Event received: " + paymentCreatedEvent.toString());
    }
}