package micro.qa.microkafka.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreatedEvent {
    private UUID paymentId;
    private BigDecimal sum;
    private String createdAt;
    private UUID orderId;
    private UUID userId;
}