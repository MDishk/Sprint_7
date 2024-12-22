package ru.yandex.praktikum.data;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    //Рандомные логин, пароль и имя курьера
    public static CourierData getRandomCourier(String loginParam, String passwordParam, String firstNameParam) {
        String login = loginParam + RandomStringUtils.randomAlphabetic(6);
        String password = passwordParam + RandomStringUtils.randomNumeric(3);
        String firstName = firstNameParam + RandomStringUtils.randomAlphabetic(4);

        return new CourierData(login, password, firstName);
    }

    public static CourierData getRandomCourier(String loginParam, String passwordParam) {
        String login = loginParam + RandomStringUtils.randomAlphabetic(6);
        String password = passwordParam + RandomStringUtils.randomNumeric(3);

        return new CourierData(login, password);
    }
}