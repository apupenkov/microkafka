package micro.qa.microkafka.api.payment;

import micro.qa.microkafka.db.model.PaymentJson;

import java.util.UUID;

public interface PaymentClient {
    String createPayment(PaymentJson paymentJson);
}
