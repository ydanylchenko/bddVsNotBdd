package com.saucedemo;

import com.saucedemo.context.Context;
import com.saucedemo.selenium.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.MalformedURLException;

import static com.saucedemo.selenium.SeleniumConfig.getConfig;

public class BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);
    private static ThreadLocal<Context> context = ThreadLocal.withInitial(Context::new);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal();

    /**
     * Getting of pre-configured {@link WebDriver} instance.
     *
     * @return webdriver object
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    public static Context getContext() {
        return context.get();
    }

    @BeforeMethod
    public void beforeTest() throws InterruptedException, MalformedURLException {
        LOG.info("Cucumber startup hooks are executed");
//        Add scenario name to user data for screenshot folder creation
        LOG.info("Starting WebDriver");
        driver.set(WebDriverFactory.startBrowser(getContext()));
        LOG.info("WebDriver ready? {}", getDriver().getCurrentUrl().isEmpty());
        {
            String threadName = Thread.currentThread().getName();
            LOG.info("Thread name: " + threadName);
            if (!threadName.replaceAll("\\D+", "").isEmpty()) {
                int sleepTime = Integer.parseInt(threadName.replaceAll("\\D+", "")) * 200;
                LOG.info("Sleeping for " + sleepTime + " ms as per thread identifier");
                Thread.sleep(sleepTime);
            }
        }
        LOG.info("Cucumber startup hooks execution is done");
    }

    /**
     * Method for screenshot taking. It is empty now, because you could save your screenshot as you want.
     * This method calls in tests listeners on test fail
     */
    @AfterMethod
    public void afterTest() throws IOException {
        LOG.info("Cucumber shutdown hooks are executed");
        try {
                LOG.error("URL on witch test was failed {}", getDriver().getCurrentUrl());
                String pageName = getContext().getPageName();
                {
                    LOG.info("Logging network errors");
                    getDriver()
                            .manage()
                            .logs()
                            .get("driver")
                            .getAll()
                            .stream()
                            .filter(logEntry -> logEntry.toJson().get("message").toString().contains("\"error\""))
                            .forEach(entry -> LOG.error("Console Error:\n{}", entry));
                    LOG.info("Network errors logged");
                }
        } finally {
            WebDriverFactory.finishBrowser(getContext(), getDriver());
            driver.remove();
            context.remove();
        }
        LOG.info("Cucumber shutdown hooks execution is done");
    }
}
