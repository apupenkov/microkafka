package micro.qa.microkafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentJson {
    private UUID paymentId;
    private BigDecimal sum;
    private String createdAt;
    private UUID orderId;
    private UUID userId;
}
