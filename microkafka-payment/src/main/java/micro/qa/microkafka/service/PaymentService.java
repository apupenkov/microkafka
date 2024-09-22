package micro.qa.microkafka.service;


import micro.qa.microkafka.dto.PaymentJson;

import java.util.concurrent.ExecutionException;

public interface PaymentService {
    String create(PaymentJson paymentJson) throws ExecutionException, InterruptedException;
}