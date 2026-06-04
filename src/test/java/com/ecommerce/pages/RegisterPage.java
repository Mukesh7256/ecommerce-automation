package com.ecommerce.pages;

import com.ecommerce.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {

    private WebDriver driver;
    private WaitUtils wait;

    // T066: All 3 types of locators
    private By nameField     = By.cssSelector("input[name='name']");
    private By emailField    = By.cssSelector("input[name='email']");
    private By passField     = By.cssSelector("input[name='password']");
    private By confirmField  = By.cssSelector("input[name='confirmPassword']");
    private By submitBtn     = By.cssSelector("button[type='submit']");
    private By errorMsg      = By.xpath("//*[contains(@class,'error')]");
    private By successMsg    = By.xpath("//*[contains(@class,'success')]");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WaitUtils(driver);
    }

    public void fillForm(String name, String email,
                         String pass, String confirm) {
        wait.waitForElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passField).sendKeys(pass);
        driver.findElement(confirmField).sendKeys(confirm);
    }

    public void submit() {
        driver.findElement(submitBtn).click();
        wait.sleep(2000);
    }

    public String getError() {
        try {
            return wait.waitForElement(errorMsg).getText();
        } catch (Exception e) { return ""; }
    }

    public String getSuccess() {
        try {
            return wait.waitForElement(successMsg).getText();
        } catch (Exception e) { return ""; }
    }
}