package com.ecommerce.pages;

import com.ecommerce.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * T066: Locators — id, xpath, css selectors
 * T065: Page Object Model
 */
public class LoginPage {

    private WebDriver driver;
    private WaitUtils wait;

    // T066: CSS Selector locators
    private By emailField    = By.cssSelector("input[name='email']");
    private By passwordField = By.cssSelector("input[name='password']");
    private By submitBtn     = By.cssSelector("button[type='submit']");

    // T066: XPath locators
    private By loginLink  = By.xpath("//span[contains(text(),'Login')]");
    private By errorMsg   = By.xpath("//*[contains(@class,'error')]");
    private By successMsg = By.xpath("//*[contains(@class,'success')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WaitUtils(driver);
    }

    public void goToLogin() {
        try {
            wait.waitForClickable(loginLink).click();
            wait.sleep(1000);
        } catch (Exception e) {
            System.out.println("Already on login page");
        }
    }

    public void enterEmail(String email) {
        wait.waitForElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickSubmit() {
        driver.findElement(submitBtn).click();
    }

    public void login(String email, String password) {
        goToLogin();
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
        wait.sleep(2000);
    }

    public String getErrorText() {
        try {
            return wait.waitForElement(errorMsg).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isLoginSuccessful() {
        wait.sleep(2000);
        String src = driver.getPageSource();
        return src.contains("ShopEasy") && !src.contains("Login here");
    }
}