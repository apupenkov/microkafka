package micro.qa.microkafka.db.repository;

import micro.qa.microkafka.db.model.PaymentEntity;

public interface PaymentRepository {

    PaymentEntity createPayment(PaymentEntity entity);
}
