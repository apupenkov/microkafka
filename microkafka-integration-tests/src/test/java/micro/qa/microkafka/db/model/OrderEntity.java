package micro.qa.microkafka.db.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;
    private BigDecimal amount;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    // OrderEntity является ведущей сущность, поэтому задает правила удаления для PaymentEntity
    @OneToOne(fetch = EAGER, orphanRemoval = true, cascade = ALL, mappedBy = "order")
    private PaymentEntity payment;

    // Геттеры и сеттеры
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = parseDate(createdAt);
    }

    public UserEntity getUserId() {
        return user;
    }

    public void setUserId(UserEntity user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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