package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.config.Config;
import com.ecommerce.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1,
          description = "TC-001: Valid login")
    public void testValidLogin() {
        System.out.println("▶ TC-001: Valid Login");
        LoginPage lp = new LoginPage(driver);
        lp.login(Config.VALID_EMAIL, Config.VALID_PASSWORD);

        Assert.assertTrue(lp.isLoginSuccessful(),
            "Login should succeed!");
        System.out.println("✅ TC-001 PASSED");
    }

    @Test(priority = 2,
          description = "TC-002: Wrong password")
    public void testWrongPassword() {
        System.out.println("▶ TC-002: Wrong Password");
        LoginPage lp = new LoginPage(driver);
        lp.login(Config.VALID_EMAIL, "wrongpass");

        String error = lp.getErrorText();
        Assert.assertFalse(error.isEmpty(),
            "Error should appear!");
        System.out.println("✅ TC-002 PASSED | Error: " + error);
    }

    @Test(priority = 3,
          description = "TC-003: Empty fields")
    public void testEmptyFields() {
        System.out.println("▶ TC-003: Empty Fields");
        LoginPage lp = new LoginPage(driver);
        lp.goToLogin();
        lp.clickSubmit();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("5173"),
            "Should stay on page!"
        );
        System.out.println("✅ TC-003 PASSED");
    }
}