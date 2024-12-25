import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.api.CourierApi;
import ru.yandex.praktikum.data.CourierData;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;

public class LoginCourierTest {

    private CourierData newCourier;
    private CourierApi courierApi = new CourierApi();
    private Integer courierId;

    @Before
    public void setUp() {
        newCourier = new CourierData();
        courierApi.createCourier(newCourier);

        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);
        courierId = loginResponse.extract().body().path("id");
    }

    @After
    public void cleanUp() {
        if (courierId != null) {
            courierApi.deleteCourier(courierId);
        }
    }

    @Test
    @Description("Проверка на то, что курьер можен залогиниться")
    @DisplayName("Авторизация курьера")
    public void loginCourierTest() {
        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);

        loginResponse
                .log().all()
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @Description("Для авторизации курьера нужно передать все обязательные поля")
    @DisplayName("Ошибка авторизации курьера без логина")
    public void failLoginCourierWithoutLoginTest() {
        newCourier.setLogin(null);

        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);
        loginResponse
                .log().all()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @Description("Для авторизации курьера нужно передать все обязательные поля")
    @DisplayName("Ошибка авторизации курьера без пароля")
    public void failLoginCourierWithoutPasswordTest() {
        newCourier.setPassword(null);
        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);

        loginResponse
                .log().all()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @Description("Система вернёт ошибку, если неправильно указать логин или пароль")
    @DisplayName("Ошибка авторизации курьера с неправильным логином")
    public void failLoginCourierWithWrongLoginTest() {
        newCourier.setLogin("pupupu");
        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);

        loginResponse
                .log().all()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @Description("Система вернёт ошибку, если неправильно указать логин или пароль")
    @DisplayName("Ошибка авторизации курьера с неправильным паролем")
    public void failLoginCourierWithWrongPasswordTest() {
        newCourier.setPassword("9999");
        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);

        loginResponse
                .log().all()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }
}