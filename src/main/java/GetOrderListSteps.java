import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class GetOrderListSteps {
    private static final String HOST = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDERS = "/v1/orders";

    @Step("Отправляем GET-запрос на " + HOST + ORDERS)
    public ValidatableResponse getOrderList(Integer courierId, String nearestStation, Integer limit, Integer page) {
        return given().log().ifValidationFails()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .queryParams("courierId", courierId)
                .queryParams("nearestStation", nearestStation)
                .queryParams("limit", limit)
                .queryParams("page", page)
                .when()
                .get(ORDERS)
                .then();

    }

}
