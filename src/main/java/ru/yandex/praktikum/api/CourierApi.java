package ru.yandex.praktikum.api;

import ru.yandex.praktikum.data.CourierData;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierApi extends RestApi {

    public static final String CREATE_COURIER = "api/v1/courier";
    public static final String LOGIN_COURIER = "api/v1/courier/login";
    public static final String DELETE_COURIER = "api/v1/courier/:id";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(CourierData newCourier) {
       return given()
                .spec(requestSpecification())
                .and().body(newCourier)
                .when().post(CREATE_COURIER)
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(CourierData newCourier) {
        return given()
                .spec(requestSpecification())
                .and().body(newCourier)
                .when().post(LOGIN_COURIER)
                .then();
    }

    @Step("Удаление курьера")
    public void deleteCourier(int courierId) {
        given()
                .spec(requestSpecification())
                .when()
                .delete(DELETE_COURIER.replace(":id", String.valueOf(courierId)));
    }
}
