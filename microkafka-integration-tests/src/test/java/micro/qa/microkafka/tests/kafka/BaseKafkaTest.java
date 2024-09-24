package micro.qa.microkafka.tests.kafka;


import micro.qa.microkafka.db.repository.PaymentRepository;
import micro.qa.microkafka.db.repository.PaymentRepositoryImpl;
import micro.qa.microkafka.jupiter.annotation.KafkaTest;
import org.junit.jupiter.api.AfterAll;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@KafkaTest
public abstract class BaseKafkaTest {

    private static final PaymentRepository paymentRepository = new PaymentRepositoryImpl();
    final static List<UUID> payments = new CopyOnWriteArrayList<>();

    @AfterAll
    static void cleanTestData() {
        paymentRepository.removeAllPayments(payments);
    }
}
