import dto.request.CreateOrder;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private CreateOrderSteps createOrderSteps = new CreateOrderSteps();

    String firstName;
    String lastName;
    String address;
    String metroStation;
    String phone;
    String rentTime;
    String deliveryDate;
    String comment;
    private String[] color;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new String[]{"BLACK", "GREY"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"null"}},
        });
    }

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверяем, создаётся ли заказ при правильном заполнении полей")
    public void responseShouldContentTrackNumber() {
        firstName = RandomStringUtils.randomAlphabetic(10);
        lastName = RandomStringUtils.randomAlphabetic(10);
        address = RandomStringUtils.randomAlphabetic(10);
        metroStation = RandomStringUtils.randomAlphabetic(10);
        phone = RandomStringUtils.randomNumeric(11);
        rentTime = RandomStringUtils.randomNumeric(1);
        deliveryDate = "2025-03-12";
        comment = RandomStringUtils.randomAlphabetic(10);

        CreateOrder request = new CreateOrder(firstName, lastName, address, metroStation,
                phone, rentTime, deliveryDate, comment, color);
        createOrderSteps
                .createOrder(request)
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }
}
