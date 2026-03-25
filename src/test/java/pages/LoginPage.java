package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class LoginPage {

    private SelenideElement usernameInput = $("#username");
    private SelenideElement passwordInput = $("#password");
    private SelenideElement loginButton = $("button[type='submit']");
    private SelenideElement flashMessage = $("#flash");

    public LoginPage open() {
        Selenide.open("https://the-internet.herokuapp.com/login");
        return this;
    }

    public LoginPage setUsername(String username) {
        usernameInput.clear();
        usernameInput.setValue(username);
        return this;
    }

    public LoginPage setPassword(String password) {
        passwordInput.clear();
        passwordInput.setValue(password);
        return this;
    }

    public LoginPage clickLogin() {
        loginButton.click();
        return this;
    }

    public boolean isLoggedIn() {
        return flashMessage.shouldBe(visible).getText().contains("You logged into a secure area!");
    }

    public String getFlashMessage() {
        return flashMessage.getText();
    }
}