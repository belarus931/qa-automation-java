package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class DropdownPage {

    private SelenideElement dropdown = $("#dropdown");

    public DropdownPage open() {
        Selenide.open("https://the-internet.herokuapp.com/dropdown");
        return this;
    }

    public DropdownPage selectOptionByValue(String value) {
        dropdown.selectOptionByValue(value);
        return this;
    }

    public DropdownPage selectOptionByText(String text) {
        dropdown.selectOption(text);
        return this;
    }

    public String getSelectedOptionText() {
        return dropdown.getSelectedOption().getText();
    }
}