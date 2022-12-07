package helpers;

import com.github.javafaker.Faker;

public class StepGenerator {
    public final String generateStep() {
        Faker faker = new Faker();
        return String.valueOf(faker.name().name());
    }
}
