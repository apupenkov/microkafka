package micro.qa.microkafka.config;

import org.aeonbits.owner.ConfigFactory;

import java.util.HashMap;
import java.util.Map;

public enum Config {

    AUTH("auth"), USER_DATA("userdata");

    private final String env = System.getProperty("env");
    private final String name;

    Config(String name) {
        this.name = name;
    }

    public ConfigProps config() {
        Map<String, String> serviceName = new HashMap<>();
        ConfigFactory.setProperty("env", env);
        serviceName.put("service.name", Config.this.getName());
        return ConfigFactory.create(ConfigProps.class, serviceName);
    }

    public String getName() {
        return name;
    }
}
