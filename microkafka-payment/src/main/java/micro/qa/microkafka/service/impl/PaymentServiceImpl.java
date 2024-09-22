package micro.qa.microkafka.service.impl;

import micro.qa.microkafka.db.model.OrderEntity;
import micro.qa.microkafka.db.model.PaymentEntity;
import micro.qa.microkafka.db.model.UserEntity;
import micro.qa.microkafka.db.repository.*;
import micro.qa.microkafka.dto.PaymentJson;
import micro.qa.microkafka.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final KafkaTemplate<String, PaymentJson> kafkaTemplate;
    private final PaymentRepository paymentRepository = new PaymentRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final OrderRepository orderRepository = new OrderRepositoryImpl();

    @Autowired
    public PaymentServiceImpl(KafkaTemplate<String, PaymentJson> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String create(PaymentJson paymentJson) throws ExecutionException, InterruptedException {

        UserEntity userEntity = userRepository.findByIdInUsers(paymentJson.getUserId());
        System.out.println(userEntity.getUsername() + ": " + userEntity.getId());

        OrderEntity orderEntity = orderRepository.findByIdInOrder(paymentJson.getOrderId());
        System.out.println(orderEntity.getAmount() + ": " + orderEntity.getId());

        PaymentEntity entity = new PaymentEntity();
        entity.setSum(paymentJson.getSum());
        entity.setCreatedAt(paymentJson.getCreatedAt());
        entity.setOrder(orderEntity);
        entity.setUser(userEntity);

        PaymentEntity paymentEntity = paymentRepository.createPayment(entity);
        System.out.println(paymentEntity.getUser() + ": " + paymentEntity.getId() + ": " + paymentEntity.getCreatedAt());

        PaymentJson paymentEvent = new PaymentJson(
//                paymentEntity.getId(),
//                paymentEntity.getSum(),
//                paymentEntity.getCreatedAt(),
//                orderEntity.getId(),
//                userEntity.getId()
                paymentEntity.getId(),
                paymentJson.getSum(),
                paymentJson.getCreatedAt(),
                paymentJson.getOrderId(),
                paymentJson.getUserId()
        );

        SendResult<String, PaymentJson> result = kafkaTemplate
                .send("payment-created-event-topic", paymentEvent.getPaymentId().toString(), paymentEvent).get();

        System.out.println("Topic " + result.getRecordMetadata().topic());
        System.out.println("Partition " + result.getRecordMetadata().partition());
        System.out.println("Offset " + result.getRecordMetadata().offset());
        System.out.println("Timestamp " + result.getRecordMetadata().timestamp());

        System.out.println("PaymentId: " + paymentEvent.getPaymentId().toString());
        return paymentEvent.getPaymentId().toString();
    }
}