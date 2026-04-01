package webtests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import io.qameta.allure.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI Тестирование")
@Feature("Авторизация")
public class LoginTest extends BaseUiTest {

    @Test
    @Tag("smoke")
    @Story("Успешный вход")
    @DisplayName("Успешный вход")
    void successfulLogin() {
        LoginPage loginPage = new LoginPage();

        loginPage.open()
                .setUsername("tomsmith")
                .setPassword("SuperSecretPassword!")
                .clickLogin();

        assertTrue(loginPage.isLoggedIn(), "Должно быть сообщение об успешном входе");
    }

    @Test
    @Tag("smoke")
    @Story("Неуспешный вход")
    @DisplayName("Неуспешный вход (неверный пароль)")
    void unsuccessfulLogin() {
        LoginPage loginPage = new LoginPage();

        loginPage.open()
                .setUsername("tomsmith")
                .setPassword("wrong")
                .clickLogin();

        String message = loginPage.getFlashMessage();
        assertTrue(message.contains("Your password is invalid"), "Сообщение должно указывать на неверный пароль");
    }
}