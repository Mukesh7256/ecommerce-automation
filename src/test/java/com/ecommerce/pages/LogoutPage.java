package com.ecommerce.pages;

import com.ecommerce.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * T076: Logout Page Object
 * T077: Session handling
 */
public class LogoutPage {

    private WebDriver driver;
    private WaitUtils wait;
    private WebDriverWait explicitWait;
    private JavascriptExecutor js;

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        this.explicitWait = new WebDriverWait(
            driver, Duration.ofSeconds(2)
        );
        this.js = (JavascriptExecutor) driver;
    }

    // T076: Click Logout button
    public void clickLogout() {
        try {
            WebElement logoutBtn = explicitWait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath(
                        "//button[contains(text(),'Logout')]"
                    )
                )
            );
            logoutBtn.click();
            wait.sleep(1000);
            System.out.println("✅ Logout button clicked");
        } catch (Exception e) {
            System.out.println("Logout btn error: " + e.getMessage());
        }
    }

    // T077: Check token in localStorage
    public String getTokenFromStorage() {
        try {
            Object token = js.executeScript(
                "return localStorage.getItem('token');"
            );
            return token != null ? token.toString() : "";
        } catch (Exception e) {
            return "";
        }
    }

    // T077: Check name in localStorage
    public String getNameFromStorage() {
        try {
            Object name = js.executeScript(
                "return localStorage.getItem('name');"
            );
            return name != null ? name.toString() : "";
        } catch (Exception e) {
            return "";
        }
    }

    // T077: Check email in localStorage
    public String getEmailFromStorage() {
        try {
            Object email = js.executeScript(
                "return localStorage.getItem('email');"
            );
            return email != null ? email.toString() : "";
        } catch (Exception e) {
            return "";
        }
    }

    // T077: Set token manually in localStorage
    public void setTokenInStorage(String token) {
        js.executeScript(
            "localStorage.setItem('token', arguments[0]);", token
        );
    }

    // T077: Clear localStorage manually
    public void clearStorage() {
        js.executeScript("localStorage.clear();");
        wait.sleep(500);
    }

    // T077: Refresh page
    public void refreshPage() {
        driver.navigate().refresh();
        wait.sleep(1000);
    }

    // Check if on login/register page
    public boolean isOnAuthPage() {
        wait.sleep(1000);
        String src = driver.getPageSource();
        return src.contains("Login here") ||
               src.contains("Create Account") ||
               src.contains("Login") &&
               !src.contains("Shop Now");
    }

    // Check if on dashboard/home
    public boolean isOnDashboard() {
        wait.sleep(1000);
        String src = driver.getPageSource();
        return src.contains("Shop Now") ||
               src.contains("ShopEasy") &&
               src.contains("Logout");
    }
}