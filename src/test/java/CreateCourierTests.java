import dto.request.CourierBase;
import dto.request.CreateCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;

import static org.hamcrest.CoreMatchers.is;


public class CreateCourierTests {
    private CourierSteps courierSteps = new CourierSteps();
    private String login;
    private String password;
    private String firstName;

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяем, создаётся ли курьер при правильном заполнении полей")
    public void shouldReturnOkTrue() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);
        CreateCourier request = new CreateCourier(login, password, firstName);

        courierSteps
                .createCourier(request)
                .statusCode(SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Создание нового курьера без логина")
    @Description("Проверяем, возвращается ли ошибка при не заполненном обязательном поле")
    public void mandatoryFieldsShouldBeFilled() {
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);
        CreateCourier request = new CreateCourier("", password, firstName);

        courierSteps
                .createCourier(request)
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверяем, возвращается ли ошибка при дублировании логина курьера")
    public void duplicateLoginNotAllowed() {
        login = "login";
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);
        CreateCourier request = new CreateCourier(login, password, firstName);

        courierSteps
                .createCourier(request);
        courierSteps
                .createCourier(request)
                .statusCode(SC_CONFLICT)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));

    }

    @After
    public void tearDown() {
        CourierBase request = new CourierBase(login, password);

        Integer id = courierSteps.loginCourier(request)
                .extract().body().path("id");
        if (id != null) {
            courierSteps.deleteCourier(id);
        }
    }
}