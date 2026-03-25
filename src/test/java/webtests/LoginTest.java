package webtests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты формы входа")
public class LoginTest extends BaseUiTest {

    @Test
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