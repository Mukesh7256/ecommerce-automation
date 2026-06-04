package com.ecommerce.pages;

import com.ecommerce.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * T067: Navigation Page - For navigation tests
 */
public class NavigationPage {

    private WebDriver driver;
    private WaitUtils wait;

    // T066: XPath + CSS locators
    private By shopNowBtn    = By.cssSelector(".btn-hero-primary");
    private By cartBtn       = By.xpath("//div[text()='Cart']/..");
    private By ordersBtn     = By.xpath("//div[text()='Orders']/..");
    private By profileBtn    = By.xpath("//div[text()='Profile']/..");
    private By searchInput   = By.cssSelector(".navbar-search input");
    private By searchSubmit  = By.cssSelector(".navbar-search button");
    private By heroTitle     = By.cssSelector(".hero-title");
    private By categoryCards = By.cssSelector(".category-card");
    private By productCards  = By.cssSelector(".product-card");
    private By footerSection = By.cssSelector(".footer");

    public NavigationPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WaitUtils(driver);
    }

    public boolean isHomePageLoaded() {
        try {
            return wait.waitForElement(heroTitle).isDisplayed();
        } catch (Exception e) { return false; }
    }

    public void clickShopNow() {
        wait.waitForClickable(shopNowBtn).click();
        wait.sleep(1000);
    }

    public void clickCart() {
        wait.waitForClickable(cartBtn).click();
        wait.sleep(1000);
    }

    public void clickOrders() {
        wait.waitForClickable(ordersBtn).click();
        wait.sleep(1000);
    }

    public void clickProfile() {
        wait.waitForClickable(profileBtn).click();
        wait.sleep(1000);
    }

    public void searchProduct(String keyword) {
        wait.waitForElement(searchInput).sendKeys(keyword);
        driver.findElement(searchSubmit).click();
        wait.sleep(1500);
    }

    public int getProductCount() {
        return driver.findElements(productCards).size();
    }

    public int getCategoryCount() {
        return driver.findElements(categoryCards).size();
    }

    public boolean isFooterVisible() {
        try {
            return driver.findElement(footerSection).isDisplayed();
        } catch (Exception e) { return false; }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}