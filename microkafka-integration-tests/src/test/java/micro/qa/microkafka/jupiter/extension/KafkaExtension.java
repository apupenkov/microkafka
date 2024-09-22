package micro.qa.microkafka.jupiter.extension;

import micro.qa.microkafka.kafka.KafkaConsumerService;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaExtension implements SuiteExtension {

    private static final KafkaConsumerService kafkaConsumerService = new KafkaConsumerService();
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void beforeSuite(ExtensionContext context) {
        executorService.execute(new KafkaConsumerService());
        if(!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    @Override
    public void afterSuite() {
        kafkaConsumerService.shutdown();
    }
}
