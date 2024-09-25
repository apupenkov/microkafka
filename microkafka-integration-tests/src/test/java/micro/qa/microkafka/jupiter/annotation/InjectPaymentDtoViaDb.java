package micro.qa.microkafka.jupiter.annotation;

import micro.qa.microkafka.jupiter.extension.InjectPaymentDtoViaDbExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(InjectPaymentDtoViaDbExtension.class)
public @interface InjectPaymentDtoViaDb {
    String password();
    String amount();
}
