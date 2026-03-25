package pages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SearchResultsPage {

    // DuckDuckGo: каждый результат — в <article> (или div с классом result)
    private ElementsCollection results = $$("article[data-testid='result']");
    // Заголовок результата — внутри h2 с классом
    private ElementsCollection resultTitles = $$("h2 a span");

    // Альтернативные селекторы, если структура меняется
    // private ElementsCollection results = $$(".result");
    // private ElementsCollection resultTitles = $$(".result__title a");

    public SearchResultsPage waitForResults() {
        results.first().shouldBe(visible);
        return this;
    }

    public int getResultsCount() {
        return results.size();
    }

    public boolean hasResults() {
        return !results.shouldHave(sizeGreaterThan(0)).isEmpty();
    }

    public String getFirstResultText() {
        // Берём текст из первого заголовка
        return resultTitles.first().text();
    }

    public ElementsCollection getAllResults() {
        return results;
    }

    public boolean hasResultContaining(String text) {
        return !resultTitles.filterBy(com.codeborne.selenide.Condition.text(text)).isEmpty();
    }

    public java.util.List<String> getAllResultsText() {
        return resultTitles.texts();
    }
}