package com.udacity.jwdnd.course1.cloudstorage;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignUpAndLogInFlowTests {


    @Autowired
    private HelperTestMethods helperTestMethods;


    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }


    /**
     * Write a Selenium test that verifies that the home page is not accessible without logging in.
     */

    @Test
    public void testHomePageNotAccessibleWithoutLoginIn() {
        driver.get(helperTestMethods.LOCALHOST + this.port + "/home");
        Assertions.assertTrue(driver.getTitle().equals(helperTestMethods.LOGIN_T));
    }


    /**
     * Write a Selenium test that signs up a new user, logs that user in, verifies that they can access the home page,
     * then logs out and verifies that the home page is no longer accessible
     */

    @Test
    public void testAccessToHomeWhenLoggedInAndInaccessibleWhenLoggedOut() {
        helperTestMethods.helperSignUp(
            helperTestMethods.NAME,
            helperTestMethods.LASTNAME,
            helperTestMethods.USERNAME,
            helperTestMethods.PASSWORD,
            this.driver,
            this.port
        );
        // Signs up and then it's redirected to login screen
        WebDriverWait webDriverWaitLoginPage = new WebDriverWait(driver, 2);
        webDriverWaitLoginPage.until(ExpectedConditions.presenceOfElementLocated(By.id("loginPage")));
        Assertions.assertEquals(helperTestMethods.LOCALHOST + this.port + "/login", driver.getCurrentUrl());

        helperTestMethods.helperLogIn(
            helperTestMethods.USERNAME,
            helperTestMethods.PASSWORD,
            this.driver,
            this.port
        );

        // Logs in and then wait to make sure we are in the home page.
        WebDriverWait webDriverWaitHomePage = new WebDriverWait(driver, 2);
        webDriverWaitHomePage.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals(helperTestMethods.LOCALHOST + this.port + "/home", driver.getCurrentUrl());

        helperTestMethods.helperLogOut(driver);

        // Logs out and waits to be back in the log in screen
        Assertions.assertEquals(helperTestMethods.LOCALHOST + this.port + "/login", driver.getCurrentUrl());

        // We attempt to go directly to home after being logged out.
        driver.get(helperTestMethods.LOCALHOST + this.port + "/home");
        Assertions.assertEquals(driver.getTitle(), helperTestMethods.LOGIN_T);

    }
}
