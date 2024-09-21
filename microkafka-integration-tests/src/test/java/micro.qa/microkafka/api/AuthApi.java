package micro.qa.microkafka.api;

import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import micro.qa.microkafka.api.AuthClient;
import micro.qa.microkafka.api.context.CookieContext;
import micro.qa.microkafka.api.context.SessionContext;
import micro.qa.microkafka.api.util.OauthUtils;
import micro.qa.microkafka.config.Config;
import micro.qa.microkafka.config.ConfigProps;
import micro.qa.microkafka.config.RestConfig;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class AuthApi implements AuthClient {

    public static final String JSESSION_ID_COOKIE_NAME = "JSESSIONID";
    public static final String XSRF_TOKEN_COOKIE_NAME = "XSRF-TOKEN";
    private final ConfigProps config = Config.AUTH.config();

    private static final RestAssuredConfig restConfig = RestConfig.getConfig();

    private final CookieContext cookieContextInstance = CookieContext.getInstance();
    private final SessionContext sessionContext = SessionContext.getInstance();
    private final String codeVerifier = OauthUtils.generateCodeVerifier();
    private final String codeChallenge = OauthUtils.generateCodeChallange(codeVerifier);

    public String getUserToken(String username, String password) {
        baseURI = config.getBaseUrl();
        final SessionContext sessionContext = SessionContext.getInstance();
        final String codeVerifier = OauthUtils.generateCodeVerifier();
        final String codeChallenge = OauthUtils.generateCodeChallange(codeVerifier);
        sessionContext.setCodeVerifier(codeVerifier);
        sessionContext.setCodeChallenge(codeChallenge);
        authorizePreRequest();
        login(username, password);
        authorizeGet();
        return getToken();
    }

    @Override
    public void authorizePreRequest() {
        Response authorizedResponse = given()
                .queryParam("response_type", "code")
                .queryParam("client_id", "client")
                .queryParam("scope", "openid")
                .queryParam("redirect_uri", "http://127.0.0.1:3001/authorized")
                .queryParam("code_challenge", SessionContext.getInstance().getCodeChallenge())
                .queryParam("code_challenge_method", "S256")
                .port(9000)
                .get("/oauth2/authorize");

        cookieContextInstance.setCookie(XSRF_TOKEN_COOKIE_NAME, authorizedResponse.cookie(XSRF_TOKEN_COOKIE_NAME));
    }

    @Override
    public void login(String username, String password) {
        Response response = given()
                .redirects().follow(false)
                .formParam("_csrf", cookieContextInstance.getCookie(XSRF_TOKEN_COOKIE_NAME))
                .formParam("username", username)
                .formParam("password", password)
                .cookie(JSESSION_ID_COOKIE_NAME, cookieContextInstance.getCookie(JSESSION_ID_COOKIE_NAME))
                .cookie(XSRF_TOKEN_COOKIE_NAME, cookieContextInstance.getCookie(XSRF_TOKEN_COOKIE_NAME))
                .port(9000)
                .post("/login");

        cookieContextInstance.setCookie(JSESSION_ID_COOKIE_NAME, response.cookie(JSESSION_ID_COOKIE_NAME));
    }

    private void authorizeGet() {
        given().config(restConfig)
            .cookie(JSESSION_ID_COOKIE_NAME, cookieContextInstance.getCookie(JSESSION_ID_COOKIE_NAME))
            .queryParam("response_type", "code")
            .queryParam("client_id", "client")
            .queryParam("scope", "openid")
            .queryParam("redirect_uri", baseURI+":" + "3001"
                    + "/authorized")
            .queryParam("code_challenge", codeChallenge)
            .queryParam("code_challenge_method", "S256")
            .queryParam("continue", "")
            .port(9000)
            .get("/oauth2/authorize");
    }

    @Override
    public String getToken() {
        Response response = given()
                .redirects().follow(false)
                .queryParam("client_id", "client")
                .queryParam("redirect_uri", baseURI+":" + "3001"
                        + "/authorized")
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", sessionContext.getCode())
                .queryParam("code_verifier", codeVerifier)
                .header("Authorization", "Basic Y2xpZW50OnNlY3JldA==")
                .port(9000)
                .post("/oauth2/token");

        return response.body().jsonPath().get("access_token");
    }

    public ConfigProps getConfig() {
        return config;
    }
}
