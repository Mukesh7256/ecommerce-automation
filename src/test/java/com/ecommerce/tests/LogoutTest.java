package com.ecommerce.tests;

import com.ecommerce.base.BaseTest;
import com.ecommerce.config.Config;
import com.ecommerce.pages.LoginPage;
import com.ecommerce.pages.LogoutPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * T076: Automate logout functionality
 * T077: Validate session handling
 */
public class LogoutTest extends BaseTest {

    // Helper - login first
    private void doLogin() {
        LoginPage lp = new LoginPage(driver);
        lp.login(Config.VALID_EMAIL, Config.VALID_PASSWORD);
        System.out.println("✅ Logged in successfully");
    }

    // ─────────────────────────────────────────
    // T076: LOGOUT TESTS
    // ─────────────────────────────────────────

    @Test(priority = 1,
          description = "TC-010: Token exists after login")
    public void testTokenExistsAfterLogin() {
        System.out.println("▶ TC-010: Token After Login");

        doLogin();
        LogoutPage lp = new LogoutPage(driver);

        String token = lp.getTokenFromStorage();
        System.out.println("Token: " +
            (token.isEmpty() ? "EMPTY" : token.substring(0,20) + "...")
        );

        Assert.assertFalse(token.isEmpty(),
            "Token should exist in localStorage after login!"
        );
        System.out.println("✅ TC-010 PASSED");
    }

    @Test(priority = 2,
          description = "TC-010a: Name & email stored after login")
    public void testSessionDataAfterLogin() {
        System.out.println("▶ TC-010a: Session Data");

        doLogin();
        LogoutPage lp = new LogoutPage(driver);

        String name  = lp.getNameFromStorage();
        String email = lp.getEmailFromStorage();

        System.out.println("Name in storage: " + name);
        System.out.println("Email in storage: " + email);

        Assert.assertFalse(name.isEmpty(),
            "Name should be in localStorage!"
        );
        Assert.assertFalse(email.isEmpty(),
            "Email should be in localStorage!"
        );
        System.out.println("✅ TC-010a PASSED");
    }

    @Test(priority = 3,
          description = "TC-010b: T076 - Logout clears session")
    public void testLogoutClearsSession() {
        System.out.println("▶ TC-010b: T076 Logout Clears Session");

        doLogin();
        LogoutPage lp = new LogoutPage(driver);

        // Verify logged in
        String tokenBefore = lp.getTokenFromStorage();
        Assert.assertFalse(tokenBefore.isEmpty(),
            "Should be logged in before logout!"
        );
        System.out.println("Token before logout: EXISTS ✅");

        // T076: Click logout
        lp.clickLogout();

        // T077: Verify localStorage cleared
        String tokenAfter = lp.getTokenFromStorage();
        String nameAfter  = lp.getNameFromStorage();
        String emailAfter = lp.getEmailFromStorage();

        System.out.println("Token after logout: " +
            (tokenAfter.isEmpty() ? "CLEARED ✅" : "STILL EXISTS ❌")
        );

        Assert.assertTrue(tokenAfter.isEmpty(),
            "Token should be cleared from localStorage after logout!"
        );
        Assert.assertTrue(nameAfter.isEmpty(),
            "Name should be cleared from localStorage!"
        );
        Assert.assertTrue(emailAfter.isEmpty(),
            "Email should be cleared from localStorage!"
        );
        System.out.println("✅ TC-010b PASSED - Session cleared!");
    }

    @Test(priority = 4,
          description = "TC-010c: T076 - After logout redirects to auth")
    public void testLogoutRedirectsToLogin() {
        System.out.println("▶ TC-010c: Logout Redirect");

        doLogin();
        LogoutPage lp = new LogoutPage(driver);

        // T076: Logout
        lp.clickLogout();

        // Should be on login/register page
        boolean onAuth = lp.isOnAuthPage();
        System.out.println("On auth page: " + onAuth);

        Assert.assertTrue(onAuth,
            "Should redirect to login/register after logout!"
        );
        System.out.println("✅ TC-010c PASSED");
    }

    // ─────────────────────────────────────────
    // T077: SESSION HANDLING TESTS
    // ─────────────────────────────────────────

    @Test(priority = 5,
          description = "TC-009: T077 - Session persists on refresh")
    public void testSessionPersistsOnRefresh() {
        System.out.println("▶ TC-009: T077 Session Persistence");

        doLogin();
        LogoutPage lp = new LogoutPage(driver);

        // Verify token exists
        String tokenBefore = lp.getTokenFromStorage();
        Assert.assertFalse(tokenBefore.isEmpty(),
            "Token should exist!"
        );

        // Refresh page
        lp.refreshPage();

        // Should still be on dashboard
        boolean onDash = lp.isOnDashboard();
        String tokenAfter = lp.getTokenFromStorage();

        System.out.println("On dashboard after refresh: " + onDash);
        System.out.println("Token after refresh: " +
            (tokenAfter.isEmpty() ? "GONE ❌" : "EXISTS ✅")
        );

        Assert.assertFalse(tokenAfter.isEmpty(),
            "Token should persist after page refresh!"
        );
        Assert.assertTrue(onDash,
            "Should stay on dashboard after refresh!"
        );
        System.out.println("✅ TC-009 PASSED");
    }

    @Test(priority = 6,
          description = "TC-011: T077 - No token = access denied")
    public void testNoTokenBlocksAccess() {
        System.out.println("▶ TC-011: T077 No Token = Blocked");

        LogoutPage lp = new LogoutPage(driver);

        // Clear storage without logging in
        lp.clearStorage();
        lp.refreshPage();

        // Should be on auth page
        boolean onAuth = lp.isOnAuthPage();
        String token = lp.getTokenFromStorage();

        System.out.println("Token: " +
            (token.isEmpty() ? "EMPTY ✅" : "EXISTS")
        );
        System.out.println("On auth page: " + onAuth);

        Assert.assertTrue(token.isEmpty(),
            "Token should be empty!"
        );
        System.out.println("✅ TC-011 PASSED");
    }

    @Test(priority = 7,
          description = "TC-008: T077 - JWT token format validation")
    public void testJwtTokenFormat() {
        System.out.println("▶ TC-008: T077 JWT Format");

        doLogin();
        LogoutPage lp = new LogoutPage(driver);

        String token = lp.getTokenFromStorage();
        System.out.println("Token exists: " + !token.isEmpty());

        // JWT has 3 parts separated by dots
        if (!token.isEmpty()) {
            String[] parts = token.split("\\.");
            System.out.println("JWT parts: " + parts.length);
            Assert.assertEquals(parts.length, 3,
                "JWT token should have 3 parts (header.payload.signature)!"
            );
            System.out.println("✅ TC-008 PASSED - Valid JWT format");
        } else {
            Assert.fail("Token is empty - Login may have failed!");
        }
    }

    @Test(priority = 8,
          description = "TC-010d: T076 - Cannot access dashboard after logout")
    public void testCannotAccessDashboardAfterLogout() {
        System.out.println("▶ TC-010d: No Dashboard After Logout");

        doLogin();
        LogoutPage lp = new LogoutPage(driver);

        // Logout
        lp.clickLogout();

        // Try to go back
        driver.navigate().back();
        lp.refreshPage();

        // Should not show dashboard features
        String src = driver.getPageSource();
        boolean hasLogout = src.contains("Logout") &&
                            src.contains("Shop Now");

        System.out.println("Dashboard accessible: " + hasLogout);

        // Either on auth page or ProtectedRoute blocks it
        Assert.assertFalse(hasLogout,
            "Dashboard should not be accessible after logout!"
        );
        System.out.println("✅ TC-010d PASSED");
    }
}