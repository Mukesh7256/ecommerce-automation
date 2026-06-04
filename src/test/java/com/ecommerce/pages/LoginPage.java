package com.ecommerce.pages;

import com.ecommerce.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WaitUtils wait;
    private WebDriverWait explicitWait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        this.explicitWait = new WebDriverWait(
            driver, Duration.ofSeconds(3)
        );
    }

    public void goToLogin() {
        // Click Login link on register page
        try {
            WebElement loginLink = explicitWait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[contains(text(),'Login here') or contains(text(),'Login')]")
                )
            );
            loginLink.click();
            wait.sleep(1500);
        } catch (Exception e) {
            System.out.println("Already on login page: " + e.getMessage());
        }
    }

    public void enterEmail(String email) {
        // Try multiple locator strategies
        WebElement el = explicitWait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@type='email' or @name='email']")
            )
        );
        el.clear();
        el.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement el = driver.findElement(
            By.xpath("//input[@type='password']")
        );
        el.clear();
        el.sendKeys(password);
    }

    public void clickSubmit() {
        driver.findElement(
            By.xpath("//button[@type='submit']")
        ).click();
    }

    public void login(String email, String password) {
        goToLogin();
        wait.sleep(1000);
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
        wait.sleep(2000); // Wait for login response
    }

    public String getErrorText() {
        try {
            return explicitWait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(@class,'error') or contains(text(),'password') or contains(text(),'Invalid')]")
                )
            ).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isLoginSuccessful() {
        wait.sleep(2000);
        try {
            // Check if navbar with ShopEasy is visible
            return driver.findElements(
                By.xpath("//*[contains(text(),'ShopEasy')]")
            ).size() > 0 ||
            driver.findElements(
                By.xpath("//*[contains(text(),'Shop Now')]")
            ).size() > 0 ||
            driver.findElements(
                By.xpath("//*[contains(text(),'Hello')]")
            ).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}