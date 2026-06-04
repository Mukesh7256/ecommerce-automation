package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.pages.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

    @Test(priority = 1,
          description = "TC-004: Valid registration")
    public void testValidRegister() {
        System.out.println("▶ TC-004: Valid Registration");
        RegisterPage rp = new RegisterPage(driver);

        String email = "test" +
            System.currentTimeMillis() + "@gmail.com";

        rp.fillForm("Test User", email, "test123", "test123");
        rp.submit();

        String src = driver.getPageSource();
        Assert.assertTrue(
            src.contains("successfully") || src.contains("Login"),
            "Registration should succeed!"
        );
        System.out.println("✅ TC-004 PASSED");
    }

    @Test(priority = 2,
          description = "TC-005: Password mismatch")
    public void testPasswordMismatch() {
        System.out.println("▶ TC-005: Password Mismatch");
        RegisterPage rp = new RegisterPage(driver);
        rp.fillForm("User", "test@gmail.com", "abc123", "xyz789");
        rp.submit();

        String src = driver.getPageSource();
        Assert.assertTrue(
            src.contains("match") || src.contains("password"),
            "Mismatch error should appear!"
        );
        System.out.println("✅ TC-005 PASSED");
    }
}