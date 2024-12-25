package ru.yandex.praktikum.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

//Класс со спецификацией, которая подходит всем тестам
public class RestApi {

    private final static String BASE_URI = "https://qa-scooter.praktikum-services.ru/";

    protected RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .build()
                .filter(new AllureRestAssured())
                .log().all();
    }
}
