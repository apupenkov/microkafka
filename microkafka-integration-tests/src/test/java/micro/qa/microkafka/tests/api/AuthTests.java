package micro.qa.microkafka.tests.api;

import io.restassured.path.json.exception.JsonPathException;
import micro.qa.microkafka.api.AuthApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthTests {

    private final AuthApi authApi = new AuthApi();

//    @Test
    void failLogin() {
        Assertions.assertThrows(JsonPathException.class,
                () -> authApi.getUserToken("sash", "12345"), "Не получилось создать токен.");
    }
}
