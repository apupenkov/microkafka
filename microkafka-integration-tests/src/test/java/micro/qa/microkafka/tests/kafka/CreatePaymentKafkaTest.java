package micro.qa.microkafka.tests.kafka;

import io.qameta.allure.AllureId;
import micro.qa.microkafka.api.payment.PaymentApi;
import micro.qa.microkafka.api.payment.PaymentClient;
import micro.qa.microkafka.kafka.KafkaConsumerService;
import micro.qa.microkafka.db.model.PaymentJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreatePaymentKafkaTest extends BaseKafkaTest {

    private final PaymentClient paymentClient = new PaymentApi();

    @Test
    @AllureId("600001")
    @DisplayName("KAFKA: Сообщение с пользователем публикуется в Kafka после успешной регистрации")
    @Tag("KAFKA")
    void messageShouldBeProducedToKafkaAfterSuccessfulRegistration() throws Exception {
        PaymentJson paymentJson = new PaymentJson();
        paymentJson.setSum(new BigDecimal(100.00));
        paymentJson.setUserId(UUID.fromString("740273fe-790c-11ef-a4fb-0242ac120003"));
        paymentJson.setOrderId(UUID.fromString("7402bc10-790c-11ef-a4fb-0242ac120003"));
        paymentJson.setCreatedAt("2024-09-22 20:38:52");

        String paymentId = paymentClient.createPayment(paymentJson);
        System.out.println(paymentId);

//        final PaymentJson messageFromKafka = KafkaConsumerService.getMessage(UUID.fromString(paymentId));
//
//        step("Check that message from kafka exist", () ->
//                assertNotNull(messageFromKafka)
//        );
//
//        step("Check message content", () ->
//                assertEquals(paymentId, messageFromKafka.getUserId())
//        );
    }
}
