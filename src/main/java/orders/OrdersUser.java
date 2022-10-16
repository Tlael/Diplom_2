package orders;

import user.Client;
import user.UsersClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static orders.OrderGenerator.*;
public class OrdersUser extends Client {
    private static final String ORDERS_USER = "/api/orders";
    public static final Order wrongOrderCheck = wrongOrder();
    public static final Order correctOrderCheck = correctOrder();


    @Step("Create order")
    public ValidatableResponse createOrder(Boolean isLogin, Order ingredients) {
        String bearerToken = "";
        if (isLogin) {
            bearerToken = UsersClient.getToken();
        }
        return given()
                .spec(Client.getBaseSpec())
                .auth().oauth2(bearerToken)
                .body(ingredients)
                .post(ORDERS_USER)
                .then();
    }

    @Step("Create order without ingredients")
    public ValidatableResponse createOrderNoIngredients() {
        String bearerToken = UsersClient.getToken();
        return given()
                .spec(Client.getBaseSpec())
                .auth().oauth2(bearerToken)
                .post(ORDERS_USER)
                .then();
    }

    @Step("Getting list of orders")
    public ValidatableResponse getOrders(Boolean isLogin) {
        String bearerToken = "";
        if (isLogin) {
            bearerToken = UsersClient.getToken();
        }
        return given()
                .spec(Client.getBaseSpec())
                .auth().oauth2(bearerToken)
                .get(ORDERS_USER).then();
    }
}