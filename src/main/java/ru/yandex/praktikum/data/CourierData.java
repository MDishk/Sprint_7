package ru.yandex.praktikum.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.datafaker.Faker;

@Data
@AllArgsConstructor
public class CourierData {

    private String login;
    private String password;
    private String firstName;

    public CourierData() {
        Faker faker = new Faker();
        this.login = faker.name().username();
        this.password = faker.internet().password();
        this.firstName = faker.name().firstName();
    }
}
