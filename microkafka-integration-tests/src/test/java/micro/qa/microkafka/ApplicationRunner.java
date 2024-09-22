package micro.qa.microkafka;

import micro.qa.microkafka.utils.MethodInvoker;

import java.net.URISyntaxException;

public class ApplicationRunner {
    public static void main(String[] args) throws URISyntaxException {
        MethodInvoker.run("micro.qa.microkafka");
    }
}
