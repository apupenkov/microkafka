package micro.qa.microkafka.api.payment;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import micro.qa.microkafka.db.dto.PaymentJson;
import micro.qa.microkafka.utils.PropertyHandler;
import org.apache.http.protocol.HTTP;

import static io.restassured.RestAssured.given;

public class PaymentApi implements PaymentClient{

    private static final String http = "http://";
    private static final String host = PropertyHandler.get("payment.address");

    @Override
    public String createPayment(PaymentJson paymentJson) {
        Response response = given()
                .contentType("application/json")
            .body(paymentJson)
        .when()
            .post( http + host +"/payment")
        .then()
            .extract().response();
        return response.getBody().asString();
    }
}
