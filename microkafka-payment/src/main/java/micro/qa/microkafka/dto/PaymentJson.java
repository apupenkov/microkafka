package micro.qa.microkafka.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentJson {
    private BigDecimal sum;
    private String createdAt;
    private UUID orderId;
    private UUID userId;
}
