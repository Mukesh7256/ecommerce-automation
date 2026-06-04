package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.config.Config;
import com.ecommerce.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1,
          description = "TC-001: Valid Login")
    public void testValidLogin() {
        System.out.println("▶ TC-001: Valid Login");
        LoginPage lp = new LoginPage(driver);
        lp.login(Config.VALID_EMAIL, Config.VALID_PASSWORD);

        boolean success = lp.isLoginSuccessful();
        System.out.println("Login result: " + success);
        System.out.println("Page title: " + driver.getTitle());
        System.out.println("Current URL: " + driver.getCurrentUrl());

        Assert.assertTrue(success,
            "Login failed! Page source: \n" +
            driver.getPageSource().substring(0, 500)
        );
        System.out.println("✅ TC-001 PASSED");
    }

    @Test(priority = 2,
          description = "TC-002: Wrong Password")
    public void testWrongPassword() {
        System.out.println("▶ TC-002: Wrong Password");
        LoginPage lp = new LoginPage(driver);
        lp.login(Config.VALID_EMAIL, "wrongpassword999");

        // Check page didn't go to dashboard
        String src = driver.getPageSource();
        boolean staysOnLogin = !src.contains("Shop Now") &&
                               !src.contains("Featured");

        Assert.assertTrue(staysOnLogin,
            "Should not login with wrong password!"
        );
        System.out.println("✅ TC-002 PASSED");
    }

    @Test(priority = 3,
          description = "TC-003: Empty Fields")
    public void testEmptyFields() {
        System.out.println("▶ TC-003: Empty Fields");
        LoginPage lp = new LoginPage(driver);
        lp.goToLogin();
        lp.clickSubmit();

        // Should stay on same page
        String src = driver.getPageSource();
        Assert.assertFalse(
            src.contains("Shop Now"),
            "Should not login with empty fields!"
        );
        System.out.println("✅ TC-003 PASSED");
    }
}