package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class GooglePage {

    // Элементы страницы
    private SelenideElement searchInput = $(By.name("q"));
    private SelenideElement firstResult = $("h3");

    // Действия
    public GooglePage open() {
        Selenide.open("/");  // ← теперь это статический метод Selenide, а не рекурсия
        return this;
    }

    public GooglePage searchFor(String query) {
        searchInput.setValue(query).pressEnter();
        return this;
    }

    public boolean hasResults() {
        return $$("h3").size() > 0;
    }

    public String getFirstResultText() {
        return firstResult.text();
    }
}