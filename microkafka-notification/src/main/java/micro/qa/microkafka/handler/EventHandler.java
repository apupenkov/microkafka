package micro.qa.microkafka.handler;

import micro.qa.microkafka.dto.PaymentJson;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    @KafkaListener(topics = "payment-created-event-topic", groupId = "payment-created-event")
    public void handle(PaymentJson paymentJson){
        System.out.println("PaymentId: " + paymentJson.getPaymentId().toString());
        System.out.println("Amount: " + paymentJson.getSum().toString());
        System.out.println("UserId: " + paymentJson.getUserId().toString());
        System.out.println("OrderId: " + paymentJson.getOrderId().toString());
        System.out.println("CreatedAt: " + paymentJson.getCreatedAt());
    }
}