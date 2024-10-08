package micro.qa.microkafka.db.model;


import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;
    @Column(nullable = false)
    private String username;
    private String password;
    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(fetch = EAGER, cascade = ALL, orphanRemoval = true, mappedBy = "user")
    private Set<OrderEntity> orders = new HashSet<>();

    /* Необходимо для добавления заказа пользователю и связи UserEntity с OrderEntity
    * Обязательно вызывать при создании заказа.
     */
    public void addOrders(OrderEntity... orderEntities) {
        for (OrderEntity orderEntity : orderEntities){
            orders.add(orderEntity);
            orderEntity.setUserId(this);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = parseDate(createdAt);
    }

    private Timestamp parseDate(String date) {
        final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return new Timestamp(DATE_TIME_FORMAT.parse(date).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}