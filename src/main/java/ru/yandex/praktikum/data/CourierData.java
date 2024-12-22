package ru.yandex.praktikum.data;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierData {

    private String login;
    private String password;
    private String firstName;

    public CourierData(String randomLogin, String randomPassword, String randomFirstName) {
        this.login = randomLogin + RandomStringUtils.randomAlphabetic(4);
        this.password = randomPassword + RandomStringUtils.randomNumeric(3);
        this.firstName = randomFirstName + RandomStringUtils.randomAlphabetic(4);
    }

    public CourierData(String randomLogin, String randomPassword) {
        this.login = randomLogin + RandomStringUtils.randomAlphabetic(4);
        this.password = randomPassword + RandomStringUtils.randomNumeric(3);
    }

    public CourierData() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
