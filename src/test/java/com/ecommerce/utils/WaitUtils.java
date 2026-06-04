package com.ecommerce.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WaitUtils {

    private WebDriver driver;
    private WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement waitForElement(By locator) {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(
            ExpectedConditions.elementToBeClickable(locator)
        );
    }

    public void sleep(int ms) {
        try { Thread.sleep(ms); } catch (Exception e) {}
    }
}