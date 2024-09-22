package micro.qa.microkafka.db.repository;

import micro.qa.microkafka.db.model.OrderEntity;

import java.util.UUID;

public interface OrderRepository {

    OrderEntity findByIdInOrder(UUID orderId);
}
