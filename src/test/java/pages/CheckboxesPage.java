package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CheckboxesPage {

    private ElementsCollection checkboxes = $$("input[type='checkbox']");

    public CheckboxesPage open() {
        Selenide.open("https://the-internet.herokuapp.com/checkboxes");
        return this;
    }

    public boolean isFirstChecked() {
        return checkboxes.first().isSelected();
    }

    public boolean isLastChecked() {
        return checkboxes.last().isSelected();
    }

    public CheckboxesPage toggleFirst() {
        checkboxes.first().click();
        return this;
    }

    public CheckboxesPage toggleLast() {
        checkboxes.last().click();
        return this;
    }
}