package ru.yandex.praktikum.api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.data.OrderData;
import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi {

    public static final String CREATE_ORDER = "api/v1/orders";
    public static final String GET_LIST_ORDERS = "api/v1/orders";
    public static final String CANCEL_ORDER = "api/v1/orders/cancel?track=id";

    @Step("POST-запрос на создание заказа")
    public ValidatableResponse createOrder(OrderData color) {
        return given()
                .spec(requestSpecification())
                .and().body(color)
                .when().post(CREATE_ORDER)
                .then();
    }

    @Step("GET-запрос на получение списка заказов")
    public ValidatableResponse getListOfOrders() {
        return given()
                .spec(requestSpecification())
                .when().get(GET_LIST_ORDERS)
                .then();
    }

    @Step("DELETE-запрос на отмену заказа")
    public void cancelOrder(int track) {
        given()
                .spec(requestSpecification())
                .when().put(CANCEL_ORDER.replace("id", String.valueOf(track)))
                .then().log().all();
    }
}

