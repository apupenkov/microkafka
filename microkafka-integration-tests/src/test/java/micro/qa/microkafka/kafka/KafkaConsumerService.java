package micro.qa.microkafka.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import micro.qa.microkafka.db.dto.PaymentJson;
import micro.qa.microkafka.utils.PropertyHandler;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static micro.qa.microkafka.utils.PropertyHandler.get;

public class KafkaConsumerService implements Runnable {

    private static final Logger LOG = Logger.getLogger(KafkaConsumerService.class.getName());
    private static final WaitForOne<String, PaymentJson> MESSAGES = new WaitForOne<>();
    private static final ObjectMapper OM = new ObjectMapper();

    private static final Properties STR_KAFKA_PROPERTIES = new Properties();
    private static long MAX_READ_TIMEOUT = 5000L;

    private final AtomicBoolean running = new AtomicBoolean(false);
    private final Consumer<String, String> stringConsumer;

    static {
        STR_KAFKA_PROPERTIES.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, get("kafka.address"));
        STR_KAFKA_PROPERTIES.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        STR_KAFKA_PROPERTIES.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        STR_KAFKA_PROPERTIES.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        STR_KAFKA_PROPERTIES.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    }

    public KafkaConsumerService() {
        this(List.of(get("kafka.topics")));
    }

    public KafkaConsumerService(List<String> stringTopics) {
        stringConsumer = new KafkaConsumer<>(STR_KAFKA_PROPERTIES);
        stringConsumer.subscribe(stringTopics);
    }

    public KafkaConsumerService withReadTimeout(long maxReadTimeout) {
        MAX_READ_TIMEOUT = maxReadTimeout;
        return this;
    }

    public void shutdown() {
        running.set(false);
    }

    public static Map<String, PaymentJson> getMessages() {
        return MESSAGES.getAsMap();
    }

    @Nullable
    public static PaymentJson getMessage(String paymentId) {
        return getMessage(paymentId, MAX_READ_TIMEOUT);
    }

    @Nullable
    public static PaymentJson getMessage(String paymentId, long timeoutMs) {
        return MESSAGES.wait(paymentId, timeoutMs);
    }

    @Nonnull
    public static PaymentJson getRequiredMessage(String paymentId) {
        return getRequiredMessage(paymentId, MAX_READ_TIMEOUT);
    }

    @Nonnull
    public static PaymentJson getRequiredMessage(String paymentId, long timeoutMs) {
        return Objects.requireNonNull(getMessage(paymentId, timeoutMs));
    }

    @Override
    public void run() {
        try {
            LOG.info("### Consumer subscribed... " + Arrays.toString(stringConsumer.subscription().toArray()) + "###");
            running.set(true);
            while (running.get()) {
                ConsumerRecords<String, String> strRecords = stringConsumer.poll(Duration.ofMillis(500));
                for (ConsumerRecord<String, String> record : strRecords) {
                    logRecord(record);
                    deserializeRecord(record.value());
                }
                try {
                    stringConsumer.commitSync();
                } catch (CommitFailedException e) {
                    LOG.warning("### Commit failed: " + e.getMessage());
                }
            }
        } finally {
            LOG.info("### Close consumer ###");
            stringConsumer.close();
            LogManager.getLogManager().reset();
            Thread.currentThread().interrupt();
        }
    }

    private void deserializeRecord(String recordValue) {
        try {
            PaymentJson paymentJson = OM.readValue(recordValue, PaymentJson.class);

            if (paymentJson == null || paymentJson.getPaymentId() == null) {
                LOG.info("### Empty username in message ###");
                return;
            }

            MESSAGES.provide(paymentJson.getPaymentId().toString(), paymentJson);
        } catch (JsonProcessingException e) {
            LOG.warning("### Parse message fail: " + e.getMessage());
        }
    }

    private void logRecord(ConsumerRecord<String, String> record) {
        LOG.info(String.format("topic = %s, \npartition = %d, \noffset = %d, \nkey = %s, \nvalue = %s\n\n",
                record.topic(),
                record.partition(),
                record.offset(),
                record.key(),
                record.value()));
    }
}
