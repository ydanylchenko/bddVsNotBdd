package com.saucedemo.pageObjects;

import com.saucedemo.BasePage;
import org.openqa.selenium.By;

import static com.saucedemo.helpers.ElementsInteraction.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SignInPage extends BasePage {
    private static final By USERNAME_INPUT = By.id("user-name");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON = By.xpath("//input[@value='LOGIN']");
    private static final By LOGO_FIELD = By.className("login_logo");
    private static final By ERROR_FIELD = By.xpath("//h3[@data-test='error']");

    public SignInPage() {
    }

    public SignInPage(boolean takeScreenShot) {
        super(takeScreenShot);
    }

    public void isPageOpened() {
        getWait().until(titleIs("Swag Labs"));
        getWait().until(visibilityOfElementLocated(LOGO_FIELD));
    }

    public void verifyIsPageOpened() {
        waitForOpen();
    }

    public void setEmail(String username) {
        sendKeys(USERNAME_INPUT, username);
    }

    public void setPassword(String password) {
        sendKeys(PASSWORD_INPUT, password);
    }

    public ProductsPage clickSignInButton() {
        click(LOGIN_BUTTON);
        return new ProductsPage(true);
    }

    public SignInPage clickSignInButtonExpectingFailure() {
        click(LOGIN_BUTTON);
        return new SignInPage(true);
    }

    public String getErrorMessage() {
        return getWait().until(visibilityOfElementLocated(ERROR_FIELD)).getText();
    }
}
