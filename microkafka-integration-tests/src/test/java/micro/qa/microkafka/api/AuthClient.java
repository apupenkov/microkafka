package micro.qa.microkafka.api;

public interface AuthClient {

    void authorizePreRequest();

    void login(String username, String password);

    String getToken();
}
