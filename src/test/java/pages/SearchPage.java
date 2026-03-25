package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class SearchPage {

    // Элементы страницы
    private SelenideElement searchInput = $(By.name("q"));
    private SelenideElement firstResult = $("article[data-testid='result']");


    // Действия
    public SearchPage open() {
        Selenide.open("/");
        return this;
    }

    public SearchResultsPage searchFor(String query) {
        searchInput.setValue(query).pressEnter();
        return new SearchResultsPage();  // ← возвращаем страницу результатов
    }

    public boolean hasResults() {
        return $$("h3").size() > 0;
    }

    public String getFirstResultText() {
        return firstResult.text();
    }
}