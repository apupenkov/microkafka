package micro.qa.microkafka.api;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import micro.qa.microkafka.api.context.SessionContext;

public class ReceivedCodeFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {

        Response resp = ctx.next(requestSpec, responseSpec);

        String location = resp.getHeader("Location");
        if(location != null && location.contains("code=")) {
            SessionContext.getInstance().setCode(location.replaceAll(".*code=", ""));
        }
        return resp;
    }

}
