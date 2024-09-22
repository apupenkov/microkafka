package micro.qa.microkafka.jupiter.extension;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public interface SuiteExtension extends BeforeAllCallback {

    void beforeSuite(ExtensionContext context);

    void afterSuite();

    @Override
    default void beforeAll(ExtensionContext context) throws Exception {
        context.getRoot().getStore(Namespace.GLOBAL)
                .getOrComputeIfAbsent(
                        SuiteExtension.class,
                        k -> {
                            beforeSuite(context);
                            return (ExtensionContext.Store.CloseableResource) this::afterSuite;
                        });
    }
}
