package micro.qa.microkafka.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserJson {
    private BigDecimal sum;
    private String createdAt;
    private String orderId;
    private String userId;
}
