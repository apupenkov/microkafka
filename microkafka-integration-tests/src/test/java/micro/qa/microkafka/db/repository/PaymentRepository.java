package micro.qa.microkafka.db.repository;

import micro.qa.microkafka.db.model.PaymentEntity;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository {

    PaymentEntity createPayment(PaymentEntity entity);

    void removeAllPayments(List<UUID> payments);
}
