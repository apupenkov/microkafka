package micro.qa.microkafka.jupiter.extension;

import io.qameta.allure.AllureId;
import micro.qa.microkafka.db.dto.PaymentJson;
import micro.qa.microkafka.db.model.OrderEntity;
import micro.qa.microkafka.db.model.UserEntity;
import micro.qa.microkafka.db.repository.OrderRepository;
import micro.qa.microkafka.db.repository.OrderRepositoryImpl;
import micro.qa.microkafka.db.repository.UserRepository;
import micro.qa.microkafka.db.repository.UserRepositoryImpl;
import micro.qa.microkafka.jupiter.annotation.InjectPaymentDtoViaDb;
import org.junit.jupiter.api.extension.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static micro.qa.microkafka.utils.GenerateRandomData.getFaker;

public class InjectPaymentDtoViaDbExtension implements BeforeEachCallback, ParameterResolver, AfterEachCallback {

    private static ExtensionContext.Namespace PAYMENT_DTO = ExtensionContext.Namespace.create(InjectPaymentDtoViaDbExtension.class);
    private UserEntity userEntity;
    private OrderEntity orderEntity;
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final OrderRepository orderRepository = new OrderRepositoryImpl();

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        InjectPaymentDtoViaDb annotation = extensionContext.getRequiredTestMethod().getAnnotation(InjectPaymentDtoViaDb.class);

        if(annotation != null) {
            userEntity = userRepository.create(getFaker().name().fullName(), annotation.password());
            orderEntity = orderRepository.create(new BigDecimal(annotation.amount()), userEntity);
            userEntity.addOrders(orderEntity);
            String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            PaymentJson paymentJson = new PaymentJson();
            paymentJson.setSum(new BigDecimal(annotation.amount()));
            paymentJson.setUserId(userEntity.getId());
            paymentJson.setOrderId(orderEntity.getId());
            paymentJson.setCreatedAt(currentDate);
            extensionContext.getStore(PAYMENT_DTO).put(getTestId(extensionContext), paymentJson);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(PaymentJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(PAYMENT_DTO).get(getTestId(extensionContext), PaymentJson.class);
    }

    private String getTestId(ExtensionContext context) {
        return Objects
                .requireNonNull(context.getRequiredTestMethod().getAnnotation(AllureId.class))
                .value();
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        if(userEntity != null) {
            // Произойдет каскадное удаление платежи -> заказы -> пользователь
            userRepository.deleteUserById(userEntity);
        }
    }
}
