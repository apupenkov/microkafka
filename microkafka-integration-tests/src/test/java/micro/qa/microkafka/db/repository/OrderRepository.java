package micro.qa.microkafka.db.repository;

import micro.qa.microkafka.db.model.OrderEntity;
import micro.qa.microkafka.db.model.UserEntity;

import java.math.BigDecimal;
import java.util.UUID;

public interface OrderRepository {

    OrderEntity findByIdInOrder(UUID orderId);

    OrderEntity create(BigDecimal amount, UserEntity user);

    void deleteOrderById(OrderEntity order);
}
