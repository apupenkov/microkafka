package micro.qa.microkafka.service.impl;

import micro.qa.microkafka.db.model.OrderEntity;
import micro.qa.microkafka.db.model.PaymentEntity;
import micro.qa.microkafka.db.model.UserEntity;
import micro.qa.microkafka.db.repository.*;
import micro.qa.microkafka.dto.PaymentJson;
import micro.qa.microkafka.event.PaymentCreatedEvent;
import micro.qa.microkafka.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final KafkaTemplate<UUID, PaymentCreatedEvent> kafkaTemplate;
    private final PaymentRepository paymentRepository = new PaymentRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final OrderRepository orderRepository = new OrderRepositoryImpl();

    @Autowired
    public PaymentServiceImpl(KafkaTemplate<UUID, PaymentCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public UUID create(PaymentJson paymentJson) throws ExecutionException, InterruptedException {

        UserEntity userEntity = userRepository.findByUserId(paymentJson.getUserId());

        OrderEntity orderEntity = orderRepository.findOrderById(paymentJson.getOrderId());

        PaymentEntity entity = new PaymentEntity();
        entity.setSum(paymentJson.getSum());
        entity.setCreatedAt(paymentJson.getCreatedAt());
        entity.setOrder(orderEntity);
        entity.setUser(userEntity);

        PaymentEntity payment = paymentRepository.createPayment(entity);

        PaymentCreatedEvent paymentCreatedEvent = new PaymentCreatedEvent(
                payment.getId(),
                payment.getSum(),
                payment.getCreatedAt(),
                orderEntity.getId(),
                userEntity.getId()
        );

        SendResult<UUID, PaymentCreatedEvent> result = kafkaTemplate
                .send("payment-created-event-topic", payment.getId(), paymentCreatedEvent).get();

        System.out.println("Topic " + result.getRecordMetadata().topic());
        System.out.println("Partition " + result.getRecordMetadata().partition());
        System.out.println("Offset " + result.getRecordMetadata().offset());
        System.out.println("Timestamp " + result.getRecordMetadata().timestamp());

        System.out.println("PaymentId: " + payment.getId());
        return payment.getId();
    }
}