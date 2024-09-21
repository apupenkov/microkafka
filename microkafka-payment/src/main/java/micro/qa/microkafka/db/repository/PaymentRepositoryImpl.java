package micro.qa.microkafka.db.repository;

import micro.qa.microkafka.db.model.PaymentEntity;
import micro.qa.microkafka.dto.PaymentJson;
import micro.qa.microkafka.jpa.JpaService;

public class PaymentRepositoryImpl extends JpaService implements PaymentRepository{

    @Override
    public PaymentEntity createPayment(PaymentEntity entity) {
        persist(entity);
        return entity;
    }
}
