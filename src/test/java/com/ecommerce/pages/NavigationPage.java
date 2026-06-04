package com.ecommerce.pages;

import com.ecommerce.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class NavigationPage {

    private WebDriver driver;
    private WaitUtils wait;
    private WebDriverWait explicitWait;

    public NavigationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        this.explicitWait = new WebDriverWait(
            driver, Duration.ofSeconds(15)
        );
    }

    public boolean isHomePageLoaded() {
        wait.sleep(3000);
        try {
            String src = driver.getPageSource();
            // Check multiple possible home page elements
            return src.contains("ShopEasy") ||
                   src.contains("Shop Now") ||
                   src.contains("Welcome") ||
                   src.contains("Featured");
        } catch (Exception e) {
            return false;
        }
    }

    public void clickShopNow() {
        try {
            explicitWait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[contains(text(),'Shop Now')]")
                )
            ).click();
            wait.sleep(2000);
        } catch (Exception e) {
            System.out.println("Shop Now not found: " + e.getMessage());
        }
    }

    public void clickCart() {
        try {
            explicitWait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[contains(text(),'Cart')]")
                )
            ).click();
            wait.sleep(2000);
        } catch (Exception e) {
            System.out.println("Cart not found: " + e.getMessage());
        }
    }

    public void clickOrders() {
        try {
            explicitWait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[contains(text(),'Orders')]")
                )
            ).click();
            wait.sleep(2000);
        } catch (Exception e) {
            System.out.println("Orders not found: " + e.getMessage());
        }
    }

    public void searchProduct(String keyword) {
        try {
            WebDriver d = driver;
            // Find search input
            explicitWait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder[contains(.,'Search')]]")
                )
            ).sendKeys(keyword);

            // Click search button
            d.findElement(
                By.xpath("//button[contains(text(),'🔍') or contains(@class,'search')]")
            ).click();
            wait.sleep(2000);
        } catch (Exception e) {
            System.out.println("Search error: " + e.getMessage());
        }
    }

    public int getProductCount() {
        wait.sleep(2000);
        return driver.findElements(
            By.xpath("//*[contains(@class,'product-card')]")
        ).size();
    }

    public int getCategoryCount() {
        return driver.findElements(
            By.xpath("//*[contains(@class,'category-card')]")
        ).size();
    }
}