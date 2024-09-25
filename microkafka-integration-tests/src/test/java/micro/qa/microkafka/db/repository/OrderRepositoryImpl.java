package micro.qa.microkafka.db.repository;

import micro.qa.microkafka.db.model.OrderEntity;
import micro.qa.microkafka.db.jpa.JpaService;
import micro.qa.microkafka.db.model.UserEntity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class OrderRepositoryImpl extends JpaService implements OrderRepository {

    @Override
    public OrderEntity findByIdInOrder(UUID uuid) {
        return entityManager.find(OrderEntity.class, uuid);
    }

    @Override
    public OrderEntity create(BigDecimal amount, UserEntity user) {
        OrderEntity order = new OrderEntity();
        order.setAmount(amount);
        order.setUserId(user);
        order.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        persist(order);
        return order;
    }

    @Override
    public void deleteOrderById(OrderEntity order) {
        remove(order);
    }
}
