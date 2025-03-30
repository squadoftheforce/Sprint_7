import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class GetOrderListTest {
    private GetOrderListSteps getOrderListSteps = new GetOrderListSteps();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверяем получение списка заказов без курьера (не требуя авторизацию)")
    public void getOrderList() {
        getOrderListSteps
                .getOrderList(null, "", 3, 1)
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }

    @Test
    @DisplayName("Получение списка заказов с несуществующим курьером")
    @Description("Проверяем корректную обработку запроса с ID несуществующего курьера")
    public void getOrderListWithNonExistentCourier() {
        int nonExistentCourierId = 999999;

        getOrderListSteps
                .getOrderList(nonExistentCourierId, "", 3, 1)
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Курьер с идентификатором " + nonExistentCourierId + " не найден"));
    }
}