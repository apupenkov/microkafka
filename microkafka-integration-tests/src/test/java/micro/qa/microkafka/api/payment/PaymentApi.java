package micro.qa.microkafka.api.payment;

import io.restassured.response.Response;
import micro.qa.microkafka.db.model.PaymentJson;

import static io.restassured.RestAssured.given;

public class PaymentApi implements PaymentClient{

    @Override
    public String createPayment(PaymentJson paymentJson) {
        Response response = given()
                .contentType("application/json")
            .body(paymentJson)
        .when()
            .post("http://localhost:9090/payment")
        .then()
            .extract().response();
        return response.getBody().asString();
    }
}
