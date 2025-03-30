import dto.request.CourierBase;
import dto.request.CreateCourier;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierSteps {
    private static final String HOST = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER = "/api/v1/courier";
    private static final String LOGIN = "/api/v1/courier/login";
    private static final String DELETE = "/api/v1/courier/{id}";

    @Step("Отправляем POST-запрос на " + HOST + COURIER)
    public ValidatableResponse createCourier(CreateCourier request) {
        return given().log().ifValidationFails()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .body(request)
                .when()
                .post(COURIER)
                .then();
    }

    @Step("Отправляем POST-запрос на " + HOST + LOGIN)
    public ValidatableResponse loginCourier(CourierBase request) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .body(request)
                .when()
                .post(LOGIN)
                .then();
    }

    @Step("Отправляем DELETE-запрос на " + HOST + DELETE)
    public ValidatableResponse deleteCourier(int id) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .pathParam("id", id)
                .when()
                .delete(DELETE)
                .then();
    }
}
