package micro.qa.microkafka.a;

import io.qameta.allure.junit5.AllureJunit5;
import micro.qa.microkafka.jupiter.extension.KafkaExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ExtendWith({KafkaExtension.class, AllureJunit5.class})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GenerateKafkaConsumer {
}
