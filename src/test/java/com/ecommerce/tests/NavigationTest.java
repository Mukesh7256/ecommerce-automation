package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.config.Config;
import com.ecommerce.pages.LoginPage;
import com.ecommerce.pages.NavigationPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * T067: Basic Navigation Tests
 */
public class NavigationTest extends BaseTest {

    // Login helper
    private void doLogin() {
        LoginPage lp = new LoginPage(driver);
        lp.login(Config.VALID_EMAIL, Config.VALID_PASSWORD);
    }

    @Test(priority = 1,
          description = "TC-021: Home page loads correctly")
    public void testHomePageLoads() {
        System.out.println("▶ TC-021: Home Page Load");
        doLogin();
        NavigationPage nav = new NavigationPage(driver);

        Assert.assertTrue(nav.isHomePageLoaded(),
            "Hero section should be visible!");
        System.out.println("✅ TC-021 PASSED");
    }

    @Test(priority = 2,
          description = "TC-022: Shop Now navigates to products")
    public void testShopNowNavigation() {
        System.out.println("▶ TC-022: Shop Now Button");
        doLogin();
        NavigationPage nav = new NavigationPage(driver);
        nav.clickShopNow();

        int count = nav.getProductCount();
        Assert.assertTrue(count > 0,
            "Products should be visible! Count: " + count);
        System.out.println("✅ TC-022 PASSED | Products: " + count);
    }

    @Test(priority = 3,
          description = "TC-023: Cart navigation works")
    public void testCartNavigation() {
        System.out.println("▶ TC-023: Cart Navigation");
        doLogin();
        NavigationPage nav = new NavigationPage(driver);
        nav.clickCart();

        String src = driver.getPageSource();
        Assert.assertTrue(
            src.contains("Cart") || src.contains("cart") ||
            src.contains("empty"),
            "Cart page should open!"
        );
        System.out.println("✅ TC-023 PASSED");
    }

    @Test(priority = 4,
          description = "TC-024: Search functionality")
    public void testSearch() {
        System.out.println("▶ TC-024: Search");
        doLogin();
        NavigationPage nav = new NavigationPage(driver);
        nav.searchProduct("iPhone");

        String src = driver.getPageSource();
        Assert.assertTrue(
            src.contains("iPhone") || src.contains("product") ||
            src.contains("found"),
            "Search should work!"
        );
        System.out.println("✅ TC-024 PASSED");
    }

    @Test(priority = 5,
          description = "TC-025: Orders page navigation")
    public void testOrdersNavigation() {
        System.out.println("▶ TC-025: Orders Navigation");
        doLogin();
        NavigationPage nav = new NavigationPage(driver);
        nav.clickOrders();

        String src = driver.getPageSource();
        Assert.assertTrue(
            src.contains("Orders") || src.contains("order") ||
            src.contains("empty"),
            "Orders page should open!"
        );
        System.out.println("✅ TC-025 PASSED");
    }

    @Test(priority = 6,
          description = "TC-026: Categories visible on home")
    public void testCategoriesVisible() {
        System.out.println("▶ TC-026: Categories");
        doLogin();
        NavigationPage nav = new NavigationPage(driver);

        int count = nav.getCategoryCount();
        Assert.assertTrue(count > 0,
            "Categories should be visible! Count: " + count);
        System.out.println("✅ TC-026 PASSED | Categories: " + count);
    }
}
