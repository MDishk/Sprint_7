import io.qameta.allure.junit4.DisplayName;
import ru.yandex.praktikum.api.OrderApi;
import ru.yandex.praktikum.data.OrderData;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private OrderData newOrder;
    private OrderApi orderApi;
    private final String[] color;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] differentColors() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {null}
        };
    }

    @Before
    public void setUp() {
        orderApi = new OrderApi();
        newOrder = new OrderData(color);
    }

    @DisplayName("Можно создать заказ с разным цветом самоката")
    @Test
    public void createOrdersWithDifferentColorsTest() {
        ValidatableResponse response = orderApi.createOrder(newOrder);

        response
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", notNullValue());
    }
}
