package ru.yandex.praktikum.api;

import ru.yandex.praktikum.data.OrderData;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi {

    public static final String CREATE_ORDER = "api/v1/orders";
    public static final String GET_LIST_ORDERS = "api/v1/orders";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(OrderData color) {
        return given()
                .spec(requestSpecification())
                .and().body(color)
                .when().post(CREATE_ORDER)
                .then();
    }

    @Step("Получение тела со списком заказов")
    public ValidatableResponse getListOfOrders() {
        return given()
                .spec(requestSpecification())
                .when().get(GET_LIST_ORDERS)
                .then();
    }
}
