package ru.yandex.praktikum.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.datafaker.Faker;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class OrderData {

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public OrderData(String[] color) {
        Faker faker = new Faker();
        this.firstName = faker.name().firstName();
        this.lastName = faker.name().lastName();
        this.address = faker.address().streetAddress();
        this.metroStation = faker.address().city();
        this.phone = faker.phoneNumber().phoneNumber();
        this.rentTime = (int) (Math.random() * 7) + 1;
        this.deliveryDate = LocalDate.now().plusDays(2).toString();
        this.comment = faker.lorem().sentence();
        this.color = color;
    }
}