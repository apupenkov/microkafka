package micro.qa.microkafka.api.payment;

import micro.qa.microkafka.db.dto.PaymentJson;

public interface PaymentClient {

    String createPayment(PaymentJson paymentJson);
}
