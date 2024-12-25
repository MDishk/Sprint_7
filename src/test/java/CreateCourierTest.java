import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.api.CourierApi;
import ru.yandex.praktikum.data.CourierData;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class CreateCourierTest {

    private CourierData newCourier;
    private CourierApi courierApi;

    @Before
    public void setUp() {
        courierApi = new CourierApi();
        newCourier = new CourierData();
    }

    @After
    public void cleanUp() {
        ValidatableResponse loginResponse = courierApi.loginCourier(newCourier);

        if (loginResponse.extract().statusCode() == SC_OK) {
            Integer courierId = loginResponse.extract().path("id");
            if (courierId != null) {
                courierApi.deleteCourier(courierId);
            }
        }
    }

    @Test
    @Description("Проверка на то, что курьера можно создать со всеми полями")
    @DisplayName("Создать курьера")
    public void createCourierTest() {
        ValidatableResponse response = courierApi.createCourier(newCourier);

        response
                .log().all()
                .assertThat()
                .statusCode(SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @Description("Проверка на то, что нельзя создать двух одинаковых курьеров")
    @DisplayName("Создать двух одинаковых курьеров")
    public void createTwoSameCouriersTest() {
        courierApi.createCourier(newCourier);
        ValidatableResponse response = courierApi.createCourier(newCourier);

        response
                .log().all()
                .assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @Description("Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    @DisplayName("Создать курьера без firstname")
    public void createCourierWithoutFirstNameTest() {
        newCourier.setFirstName(null);
        ValidatableResponse response = courierApi.createCourier(newCourier);

        response
                .log().all()
                .assertThat()
                .statusCode(SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @Description("Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    @DisplayName("Создать курьера без login")
    public void createCourierWithoutLoginTest() {
        newCourier.setLogin(null);
        ValidatableResponse response = courierApi.createCourier(newCourier);

        response
                .log().all()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    @DisplayName("Создать курьера без password")
    public void createCourierWithoutPasswordTest() {
        newCourier.setPassword(null);
        ValidatableResponse response = courierApi.createCourier(newCourier);

        response
                .log().all()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }
}
