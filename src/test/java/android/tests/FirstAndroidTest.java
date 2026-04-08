package android.tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumBy;

import java.net.URL;
import java.time.Duration;

public class FirstAndroidTest extends BaseAndroidTest {
    @Test
    @DisplayName("Open View screen test")
    void shouldOpenViewsScreen(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement viewsElement = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Views")));
        viewsElement.click();
        WebElement expandableListsElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Expandable Lists"))
        );

        assert expandableListsElement.isDisplayed();

    }

}
