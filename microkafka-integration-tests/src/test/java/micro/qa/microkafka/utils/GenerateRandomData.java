package micro.qa.microkafka.utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class GenerateRandomData {

    private static final Faker faker = new Faker(new Locale("ru"));

    public static Faker getFaker() {
        return faker;
    }
}
