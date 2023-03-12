package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AddingEditingDeletingCredentialsTest {
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

    private void createCredential(String url, String username, String password, String userIdentifier) {
        helperTestMethods.helperSignUp(
                helperTestMethods.NAME + userIdentifier,
                helperTestMethods.LASTNAME + userIdentifier,
                helperTestMethods.USERNAME + userIdentifier,
                helperTestMethods.PASSWORD + userIdentifier,
                this.driver,
                this.port
        );
        // Signs up and then it's redirected to login screen
        WebDriverWait webDriverWaitLoginPage = new WebDriverWait(driver, 2);
        webDriverWaitLoginPage.until(ExpectedConditions.presenceOfElementLocated(By.id("loginPage")));

        helperTestMethods.helperLogIn(
                helperTestMethods.USERNAME + userIdentifier,
                helperTestMethods.PASSWORD + userIdentifier,
                this.driver,
                this.port
        );

        // Logs in and then wait to make sure we are in the home page.
        WebDriverWait webDriverWaitHomePage = new WebDriverWait(driver, 2);
        webDriverWaitHomePage.until(ExpectedConditions.titleContains("Home"));

        helperTestMethods.helperNavToCred(this.driver);

        helperTestMethods.helperAddNewCredClick(this.driver);

        helperTestMethods.helperSetCred(this.driver, url, username, password);

        helperTestMethods.helperWaitForSuccessPageAndClickContinue(this.driver);

    }

    private void editCredential(String newUrl, String newUsername, String newPassword) {
        helperTestMethods.helperNavToCred(this.driver);

        helperTestMethods.helperEditCredClick(this.driver);

        helperTestMethods.helperSetCred(this.driver, newUrl, newUsername, newPassword);

        helperTestMethods.helperWaitForSuccessPageAndClickContinue(this.driver);

    }


    private void deleteCredential() {
        helperTestMethods.helperNavToCred(this.driver);

        helperTestMethods.helperDeleteCredClick(this.driver);

        helperTestMethods.helperWaitForSuccessPageAndClickContinue(this.driver);

    }

    /**
     * Write a Selenium test that logs in an existing user,
     * creates a credential and verifies that the credential
     * details are visible in the credential list.
     */

    @Test
    public void testAddingCreds() {
        String url = "www.yahoo.com";
        String username = "lhernand";
        String password = "P@ssw0rd";
        String userIdentifier = "_4";


        this.createCredential(url, username, password, userIdentifier);

        helperTestMethods.helperNavToCred(this.driver);

        WebDriverWait tableCredUrl = new WebDriverWait(driver, 5);
        tableCredUrl.until(ExpectedConditions.visibilityOfElementLocated(By.id("tableCredUrl")));

        Assertions.assertEquals(this.driver.findElement(By.id("tableCredUrl")).getText(), url);
        Assertions.assertEquals(this.driver.findElement(By.id("tableCredUsername")).getText(), username);
        Assertions.assertNotEquals(this.driver.findElement(By.id("tableCredPassword")).getText(), password);

    }

    /**
     * Write a Selenium test that logs in an existing user with existing credentials,
     * clicks the edit credential button on an existing credential,
     * changes the credential data, saves the changes, and verifies that the
     * changes appear in the credential list.
     */
    @Test
    public void testEditingCreds() {
        String url1 = "www.yahoo.com";
        String username1 = "lhernand";
        String url2 = "https://yahoo.com";
        String username2 = "lhdz";
        String password1 = "P@ssw0rd";
        String password2 = "SuperS@fe";
        String userIdentifier = "_5";


        this.createCredential(url1, username1, password1, userIdentifier);

        helperTestMethods.helperNavToCred(this.driver);

        WebDriverWait tableCredUrl = new WebDriverWait(driver, 5);
        tableCredUrl.until(ExpectedConditions.visibilityOfElementLocated(By.id("tableCredUrl")));

        Assertions.assertEquals(this.driver.findElement(By.id("tableCredUrl")).getText(), url1);
        Assertions.assertEquals(this.driver.findElement(By.id("tableCredUsername")).getText(), username1);
        Assertions.assertNotEquals(this.driver.findElement(By.id("tableCredPassword")).getText(), password1);

        helperTestMethods.helperLogOut(driver);

        helperTestMethods.helperLogIn(
                helperTestMethods.USERNAME + userIdentifier,
                helperTestMethods.PASSWORD + userIdentifier,
                this.driver,
                this.port
        );

        this.editCredential(url2, username2, password2);

        helperTestMethods.helperNavToCred(this.driver);

        WebDriverWait tableCredUrl2 = new WebDriverWait(driver, 5);
        tableCredUrl2.until(ExpectedConditions.visibilityOfElementLocated(By.id("tableCredUrl")));

        Assertions.assertEquals(this.driver.findElement(By.id("tableCredUrl")).getText(), url2);
        Assertions.assertEquals(this.driver.findElement(By.id("tableCredUsername")).getText(), username2);
        Assertions.assertNotEquals(this.driver.findElement(By.id("tableCredPassword")).getText(), password2);

    }

    /**
     * Write a Selenium test that logs in an existing user with existing credentials,
     * clicks the delete credential button on an existing credential,
     * and verifies that the credential no longer appears in the credential list.
     */
    @Test
    public void testDeletingCreds() {
        String url = "https://yahoo.com";;
        String username = "lhdz";
        String password = "SuperS@fe";
        String userIdentifier = "_6";



        this.createCredential(url, username, password, userIdentifier);

        helperTestMethods.helperNavToCred(this.driver);

        WebDriverWait tableCredUrl = new WebDriverWait(driver, 5);
        tableCredUrl.until(ExpectedConditions.visibilityOfElementLocated(By.id("tableCredUrl")));

        Assertions.assertEquals(this.driver.findElement(By.id("tableCredUrl")).getText(), url);
        Assertions.assertEquals(this.driver.findElement(By.id("tableCredUsername")).getText(), username);
        Assertions.assertNotEquals(this.driver.findElement(By.id("tableCredPassword")).getText(), password);

        helperTestMethods.helperLogOut(driver);

        helperTestMethods.helperLogIn(
            helperTestMethods.USERNAME + userIdentifier,
            helperTestMethods.PASSWORD + userIdentifier,
                this.driver,
                this.port
        );

        this.deleteCredential();

        helperTestMethods.helperNavToCred(this.driver);

        Assertions.assertFalse(helperTestMethods.helperDoesElementExist(this.driver, "tableCredUrl"));

    }
}
