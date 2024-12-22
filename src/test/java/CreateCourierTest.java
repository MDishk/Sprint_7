import io.qameta.allure.junit4.DisplayName;
import ru.yandex.praktikum.api.CourierApi;
import ru.yandex.praktikum.data.CourierData;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.is;

public class CreateCourierTest {

    private CourierData newCourier;
    private CourierApi courierApi;

    @Before
    public void setUp() {
        courierApi = new CourierApi();
    }

    @DisplayName("Курьера можно создать со всеми полями")
    @Test
    public void createCourierTest() {
        newCourier = new CourierData("Satoru_", "12", "Gojo_");
        ValidatableResponse response = courierApi.createCourier(newCourier);

        response
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));

        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);
        int courierId = loginResponse.extract().path("id");
        courierApi.deleteCourier(courierId);
    }

    @DisplayName("Двух одинаковых курьеров нельзя создать")
    @Test
    public void createTwoSameCouriersTest() {
        newCourier = new CourierData("Satoru_", "12", "Gojo_");
        courierApi.createCourier(newCourier);
        ValidatableResponse response = courierApi.createCourier(newCourier);

        response
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));

        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);
        int courierId = loginResponse.extract().path("id");
        courierApi.deleteCourier(courierId);
    }

    @DisplayName("Курьера можно создать только с обязательными полями")
    @Test
    public void createCourierWithoutFirstNameTest() {
        newCourier = new CourierData("Satoru_", "12");
        ValidatableResponse response = courierApi.createCourier(newCourier);

        response
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));

        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);
        int courierId = loginResponse.extract().path("id");
        courierApi.deleteCourier(courierId);
    }

    @DisplayName("Курьера нельзя создать без обязательного поля - логина")
    @Test
    public void createCourierWithoutLoginTest() {
        newCourier = new CourierData("Satoru_", "12", "Gojo_");
        newCourier.setLogin(null);
        ValidatableResponse response = courierApi.createCourier(newCourier);

        response
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @DisplayName("Курьера нельзя создать без обязательного поля - пароля")
    @Test
    public void createCourierWithoutPasswordTest() {
        newCourier = new CourierData("Satoru_", "12", "Gojo_");
        newCourier.setPassword(null);
        ValidatableResponse response = courierApi.createCourier(newCourier);

        response
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }
}
