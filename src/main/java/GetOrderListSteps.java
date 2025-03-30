import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class GetOrderListSteps {
    private static final String HOST = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDERS = "/api/v1/orders";

    @Step("Отправляем GET-запрос на " + HOST + ORDERS)
    public ValidatableResponse getOrderList(Integer courierId, String nearestStation, Integer limit, Integer page) {
        RequestSpecification request = given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(HOST);
            if (courierId != null) {
                request.queryParam("courierId", courierId);
            }
            if (nearestStation != null && !nearestStation.isEmpty()) {
                request.queryParam("nearestStation", nearestStation);
            }
            if (limit != null) {
                request.queryParam("limit", limit);
            }
            if (page != null) {
                request.queryParam("page", page);
            }
            return request
                .when()
                .get(ORDERS)
                .then();
    }
}