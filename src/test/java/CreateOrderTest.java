import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.api.OrderApi;
import ru.yandex.praktikum.data.OrderData;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private OrderData newOrder;
    private OrderApi orderApi;
    private final String[] color;
    private Integer track;

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

    @After
    public void cleanUp() {
        if (track != null) {
            orderApi.cancelOrder(track);
        }
    }

    @Test
    @Description("Проверка на то, что заказ можно создать с указанием цветов самоката или без них")
    @DisplayName("Создать заказ с разными цветами самоката")
    public void createOrdersWithDifferentColorsTest() {
        ValidatableResponse response = orderApi.createOrder(newOrder);

        response
                .log().all()
                .assertThat()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());

        track = response.extract().path("track");
    }
}
