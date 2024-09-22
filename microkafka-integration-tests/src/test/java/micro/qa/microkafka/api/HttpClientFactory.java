package micro.qa.microkafka.api;

import io.restassured.config.HttpClientConfig;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientFactory implements HttpClientConfig.HttpClientFactory {

    @Override
    public HttpClient createHttpClient() {
        DefaultHttpClient client = new DefaultHttpClient();
        client.addResponseInterceptor(new ReceivedCodeInterceptor());
        return client;
    }
}