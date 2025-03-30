import dto.request.CreateOrder;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private CreateOrderSteps createOrderSteps = new CreateOrderSteps();
    private Faker faker = new Faker(new Locale("ru"));

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
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        address = faker.address().streetAddress();
        metroStation = faker.address().city();
        phone = faker.numerify("79#########");
        rentTime = String.valueOf(faker.number().numberBetween(1, 7));
        deliveryDate = "2025-03-12";
        comment = faker.lorem().sentence();

        CreateOrder request = new CreateOrder(firstName, lastName, address, metroStation,
                phone, rentTime, deliveryDate, comment, color);
        createOrderSteps
                .createOrder(request)
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }
}