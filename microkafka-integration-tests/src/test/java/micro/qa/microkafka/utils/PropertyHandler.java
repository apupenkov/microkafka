package micro.qa.microkafka.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class PropertyHandler {

    private static final Properties property = getProp("config.properties");

//    static {
//        String fileConfiguration = getFileConfiguration(getProp("micro.qa.microkafka.micro.qa.microkafka.config.properties"));
//        property = getProp(fileConfiguration);
//    }

    public static String get(String key) {
        return property.getProperty(key);
    }

    private static Properties getProp(String fileName) {
        Properties property = new Properties();
        URL props = null;
        props = ClassLoader.getSystemResource(fileName);
        try {
            property.load(props.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return property;
    }

//    private static String getFileConfiguration(Properties props) {
//        return PropertyHandler.get("fileProperties");
//    }
}
