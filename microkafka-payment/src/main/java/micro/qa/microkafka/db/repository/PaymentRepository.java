package micro.qa.microkafka.db.repository;

import micro.qa.microkafka.db.model.PaymentEntity;
import micro.qa.microkafka.dto.PaymentJson;

import java.util.UUID;

public interface PaymentRepository {

    PaymentEntity createPayment(PaymentEntity entity);
}
