import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import ru.yandex.praktikum.api.OrderApi;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOfOrdersTest {

    private OrderApi orderApi;

    @Before
    public void setUp() {
        orderApi = new OrderApi();
    }

    @Test
    @Description("Проверка на то, тело ответа возвращает список заказов")
    @DisplayName("Получить список заказов")
    public void getListOfOrdersTest() {
        ValidatableResponse response = orderApi.getListOfOrders();

        response
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("listOfOrders", notNullValue());
    }
}