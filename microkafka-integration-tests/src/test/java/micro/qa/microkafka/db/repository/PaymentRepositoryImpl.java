package micro.qa.microkafka.db.repository;

import micro.qa.microkafka.db.model.PaymentEntity;
import micro.qa.microkafka.db.jpa.JpaService;

import java.util.List;
import java.util.UUID;

public class PaymentRepositoryImpl extends JpaService implements PaymentRepository{

    @Override
    public PaymentEntity createPayment(PaymentEntity entity) {
        persist(entity);
        return entity;
    }

    @Override
    public void removeAllPayments(List<UUID> payments) {
        tx(em -> em
            .createQuery("delete from PaymentEntity p where p.id in (:payments)")
            .setParameter("payments", payments)
            .executeUpdate());
    }
}
