import orders.OrdersUser;
import org.junit.After;
import user.UsersClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static orders.OrdersUser.CORRECT_ORDER_CHECK;
import static orders.OrdersUser.WRONG_ORDER_CHECK;
import static org.apache.hc.core5.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static user.UsersClient.delete;

public class CreateOrdersTest {
    @Before
    public void setUp() {
        UsersClient.createUser();
    }

    @Test
    @DisplayName("Создание заказа авторизацией")
    public void createOrdersWithAuthorizationTest() {
        OrdersUser ordersUser = new OrdersUser();
        ValidatableResponse response = ordersUser.createOrder(true, CORRECT_ORDER_CHECK);
        int statusCode = response.extract().statusCode();
        assertEquals("Статус код не 200", SC_OK, statusCode);
        response.assertThat().body("success", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuthorizationTest() {
        OrdersUser ordersUser = new OrdersUser();
        ValidatableResponse response = ordersUser.createOrder(false, CORRECT_ORDER_CHECK);
        int statusCode = response.extract().statusCode();
        assertEquals("Статус код не 200", SC_OK, statusCode);
        response.assertThat().body("success", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        OrdersUser ordersClient = new OrdersUser();
        ValidatableResponse response = ordersClient.createOrderNoIngredients();
        int statusCode = response.extract().statusCode();
        assertEquals("Orders.Ingredient ids must be provided", SC_BAD_REQUEST, statusCode);
        response.assertThat().body("success", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderWithWrongHashTest() {
        OrdersUser ordersClient = new OrdersUser();
        ValidatableResponse response = ordersClient.createOrder(true, WRONG_ORDER_CHECK);
        int statusCode = response.extract().statusCode();
        assertEquals("Internal Server Error", SC_SERVER_ERROR, statusCode);
        response.assertThat().body("html.body.pre", notNullValue());
    }

    @After
    public void deleteUser() {
        delete();
    }
}