package micro.qa.microkafka.tests.kafka;

import io.qameta.allure.AllureId;
import micro.qa.microkafka.api.payment.PaymentApi;
import micro.qa.microkafka.api.payment.PaymentClient;
import micro.qa.microkafka.db.dto.PaymentJson;
import micro.qa.microkafka.jupiter.annotation.InjectPaymentDtoViaDb;
import micro.qa.microkafka.kafka.KafkaConsumerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreatePaymentKafkaTest extends BaseKafkaTest {

    private final PaymentClient paymentClient = new PaymentApi();

    @Test
    @AllureId("6001")
    @DisplayName("KAFKA: Сообщение с пользователем публикуется в Kafka после успешной регистрации")
    @Tag("KAFKA")
    @InjectPaymentDtoViaDb(password = "qwerty123456", amount = "200.00")
    void messageShouldBeProducedToKafkaAfterSuccessfulRegistration(PaymentJson paymentJson) {

        String paymentId = paymentClient.createPayment(paymentJson);
        payments.add(UUID.fromString(paymentId));

        final PaymentJson messageFromKafka = KafkaConsumerService.getMessage(paymentId);

        step("Check that message from kafka exist", () -> assertNotNull(messageFromKafka));

        step("Check message content", () -> assertEquals(paymentId, messageFromKafka.getPaymentId().toString()));
    }
}
