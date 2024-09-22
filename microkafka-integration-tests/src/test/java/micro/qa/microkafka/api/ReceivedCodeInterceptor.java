package micro.qa.microkafka.api;

import micro.qa.microkafka.api.context.SessionContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;

import java.util.Arrays;
import java.util.Optional;

public class ReceivedCodeInterceptor implements HttpResponseInterceptor {

    @Override
    public void process(HttpResponse httpResponse, HttpContext context) {

        Optional<Header> optionalHeader = Arrays.stream(httpResponse.getHeaders("Location")).findFirst();

        if(optionalHeader.isPresent()) {
            String location = optionalHeader.get().getValue();
            if (location != null && location.contains("code=")) {
                SessionContext.getInstance().setCode(StringUtils.substringAfter(location, "code="));
            }
        }
    }
}
