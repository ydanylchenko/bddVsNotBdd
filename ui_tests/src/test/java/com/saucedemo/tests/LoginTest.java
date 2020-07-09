package com.saucedemo.tests;

import com.saucedemo.BaseTest;
import com.saucedemo.pageObjects.GlobalSteps;
import com.saucedemo.pageObjects.ProductsPage;
import com.saucedemo.pageObjects.SignInPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LoginTest extends BaseTest {
    @Test
    public void positiveLoginTest() {
        SignInPage signInPage = new GlobalSteps().openStartPage();
        signInPage.setEmail("standard_user");
        signInPage.setPassword("secret_sauce");
        ProductsPage productsPage = signInPage.clickSignInButton();
        productsPage.verifyIsPageOpened();
    }

    @Test
    public void negativeLoginTest() {
        SignInPage signInPage = new GlobalSteps().openStartPage();
        signInPage.setEmail("standard_user");
        signInPage.setPassword("thisIsSomeInvalidPassword");
        signInPage.clickSignInButtonExpectingFailure();
        String errorMessage = signInPage.getErrorMessage();
        assertEquals(errorMessage, "Epic sadface: Username and password do not match any user in this service",
                "Invalid error message");
    }
}
