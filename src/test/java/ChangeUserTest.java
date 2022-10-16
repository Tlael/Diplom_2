import user.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.hc.core5.http.HttpStatus.SC_UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class ChangeUserTest {
    protected UsersClient usersClient;

    @Before
    public void setUp() {
        usersClient = new UsersClient();
    }

    @Test
    @DisplayName("Изменение данных с авторизацией")
    public void changeTest() {
        UsersClient.createUser();
        ValidatableResponse loginResponse = UsersClient.login();
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("Статус код не 201", SC_OK, statusCode);
        boolean isTrue = loginResponse.extract().path("success");
        assertTrue(isTrue);
    }

    @Test
    @DisplayName("Изменение данных без авторизации")
    public void changeNoLoginUserTest() {
        ValidatableResponse loginResponse = UsersClient.loginNoEmailUser();
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("You should be authorised", SC_UNAUTHORIZED, statusCode);
        boolean isFalse = loginResponse.extract().path("success");
        assertFalse(isFalse);
    }
}
