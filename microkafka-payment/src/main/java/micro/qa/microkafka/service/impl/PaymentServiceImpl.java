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
        PaymentJson paymentEvent = null;

        if(paymentJson != null) {
            UserEntity userEntity = userRepository.findByIdInUsers(paymentJson.getUserId());

            OrderEntity orderEntity = orderRepository.findByIdInOrder(paymentJson.getOrderId());

            PaymentEntity entity = new PaymentEntity();
            entity.setSum(paymentJson.getSum());
            entity.setCreatedAt(paymentJson.getCreatedAt());
            entity.setOrder(orderEntity);
            entity.setUser(userEntity);

            PaymentEntity paymentEntity = paymentRepository.createPayment(entity);
            paymentEvent = new PaymentJson(
                    paymentEntity.getId(), paymentJson.getSum(), paymentJson.getCreatedAt(),
                    paymentJson.getOrderId(), paymentJson.getUserId()
            );

            SendResult<String, PaymentJson> result = kafkaTemplate
                    .send("payment-created-event-topic", paymentEvent.getPaymentId().toString(), paymentEvent).get();
        } else {
            throw new IllegalArgumentException("PaymentJson is not to by null");
        }
        return paymentEvent != null ? paymentEvent.getPaymentId().toString() : "";
    }
}