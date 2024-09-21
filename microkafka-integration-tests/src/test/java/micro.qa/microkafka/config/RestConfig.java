package micro.qa.microkafka.config;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import micro.qa.microkafka.api.HttpClientFactory;

public class RestConfig {

    public static RestAssuredConfig getConfig() {
        return RestAssured.config().httpClient(
                HttpClientConfig.httpClientConfig().httpClientFactory(
                        new HttpClientFactory()));
    }
}
