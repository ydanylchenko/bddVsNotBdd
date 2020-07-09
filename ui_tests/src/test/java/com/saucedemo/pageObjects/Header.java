package com.saucedemo.pageObjects;

import com.saucedemo.BasePage;
import org.openqa.selenium.By;

import static com.saucedemo.helpers.ElementsInteraction.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Header extends BasePage {
    private static final By CART_SIZE_FIELD = By.className("shopping_cart_badge");
    private static final By CART_LINK = By.className("shopping_cart_link");
    private static final By MENU_BUTTON = By.className("bm-burger-button");

    public Header() {
    }

    public Header(boolean takeScreenShot) {
        super(takeScreenShot);
    }

    public void isPageOpened() {
        getWait().until(visibilityOfElementLocated(CART_LINK));
    }

    public String getCartSize() {
        return getWait().until(visibilityOfElementLocated(CART_SIZE_FIELD)).getText();
    }

}
