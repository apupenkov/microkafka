package micro.qa.microkafka.service;


import micro.qa.microkafka.dto.PaymentJson;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface PaymentService {
    UUID create(PaymentJson paymentJson) throws ExecutionException, InterruptedException;
}