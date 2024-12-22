import io.qameta.allure.junit4.DisplayName;
import ru.yandex.praktikum.api.CourierApi;
import ru.yandex.praktikum.data.CourierData;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;

public class LoginCourierTest {

    private CourierData newCourier;
    private CourierApi courierApi = new CourierApi();
    private Integer courierId;

    @Before
    public void setUp() {
        newCourier = new CourierData("Satoru_", "12");
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

    @DisplayName("Курьер может авторизоваться")
    @Test
    public void loginCourierTest() {

        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);

        loginResponse
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue());
    }

    @DisplayName("Курьер не может авторизоваться без логина")
    @Test
    public void failLoginCourierWithoutLoginTest() {

        newCourier.setLogin(null);
        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);

        loginResponse
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @DisplayName("Курьер не может авторизоваться без пароля")
    @Test
    public void failLoginCourierWithoutPasswordTest() {

        newCourier.setPassword(null);
        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);

        loginResponse
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @DisplayName("Курьер не может авторизоваться с неправильным логином")
    @Test
    public void failLoginCourierWithWrongLoginTest() {

        newCourier.setLogin("pupupu");
        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);

        loginResponse
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @DisplayName("Курьер не может авторизоваться с неправильным паролем")
    @Test
    public void failLoginCourierWithWrongPasswordTest() {

        newCourier.setPassword("9999");
        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);

        loginResponse
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }
}



