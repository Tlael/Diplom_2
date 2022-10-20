import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import orders.OrdersUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.UsersClient;

import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static user.UsersClient.delete;


public class ListOrdersTest {
    @Before
    public void setUp() {
        UsersClient.createUser();
    }

    @Test
    @DisplayName("Получение списка заказов c авторизацией")
    public void listOrdersWithAuthorizationTest() {
        OrdersUser ordersClient = new OrdersUser();
        ValidatableResponse response = ordersClient.getOrders(true);
        int statusCode = response.extract().statusCode();
        assertEquals("Статус код не 200", SC_OK, statusCode);
        response.assertThat().body("success", notNullValue());
    }

    @Test
    @DisplayName("Получение списка без авторизации")
    public void listOrdersTest() {
        OrdersUser ordersClient = new OrdersUser();
        ValidatableResponse response = ordersClient.getOrders(false);
        int statusCode = response.extract().statusCode();
        assertEquals("You should be authorised", SC_UNAUTHORIZED, statusCode);
        response.assertThat().body("success", notNullValue());
    }

    @After
    public void deleteUser() {
        delete();
    }
}