package micro.qa.microkafka.db.repository;

import micro.qa.microkafka.db.model.OrderEntity;
import micro.qa.microkafka.jpa.JpaService;

import java.util.UUID;

public class OrderRepositoryImpl extends JpaService implements OrderRepository {

    @Override
    public OrderEntity findOrderById(UUID uuid) {
        return entityManager.find(OrderEntity.class, uuid);
    }
}
