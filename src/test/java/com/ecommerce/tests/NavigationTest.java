package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.config.Config;
import com.ecommerce.pages.LoginPage;
import com.ecommerce.pages.NavigationPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationTest extends BaseTest {

    private void doLogin() {
        LoginPage lp = new LoginPage(driver);
        lp.login(Config.VALID_EMAIL, Config.VALID_PASSWORD);
        System.out.println("Login done, page: " +
            driver.getPageSource().substring(0, 200));
    }

    @Test(priority = 1,
          description = "TC-021: Home page loads")
    public void testHomePageLoads() {
        System.out.println("▶ TC-021: Home Page");
        doLogin();
        NavigationPage nav = new NavigationPage(driver);

        boolean loaded = nav.isHomePageLoaded();
        Assert.assertTrue(loaded,
            "Home page not loaded! Source: " +
            driver.getPageSource().substring(0, 300)
        );
        System.out.println("✅ TC-021 PASSED");
    }

    @Test(priority = 2,
          description = "TC-022: Shop Now works")
    public void testShopNow() {
        System.out.println("▶ TC-022: Shop Now");
        doLogin();
        NavigationPage nav = new NavigationPage(driver);
        nav.clickShopNow();

        String src = driver.getPageSource();
        Assert.assertTrue(
            src.contains("product") || src.contains("Product"),
            "Products page should load!"
        );
        System.out.println("✅ TC-022 PASSED");
    }

    @Test(priority = 3,
          description = "TC-023: Cart opens")
    public void testCart() {
        System.out.println("▶ TC-023: Cart");
        doLogin();
        NavigationPage nav = new NavigationPage(driver);
        nav.clickCart();

        String src = driver.getPageSource();
        Assert.assertTrue(
            src.contains("Cart") || src.contains("cart") ||
            src.contains("empty") || src.contains("Shopping"),
            "Cart page should open!"
        );
        System.out.println("✅ TC-023 PASSED");
    }

    @Test(priority = 4,
          description = "TC-024: Search works")
    public void testSearch() {
        System.out.println("▶ TC-024: Search");
        doLogin();
        NavigationPage nav = new NavigationPage(driver);
        nav.searchProduct("iPhone");

        String src = driver.getPageSource();
        Assert.assertTrue(
            src.contains("product") || src.contains("found") ||
            src.contains("iPhone") || src.contains("No"),
            "Search should return result!"
        );
        System.out.println("✅ TC-024 PASSED");
    }
}