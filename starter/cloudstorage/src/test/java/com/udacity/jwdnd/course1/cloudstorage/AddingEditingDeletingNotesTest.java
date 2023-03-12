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

public class AddingEditingDeletingNotesTest {

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


    private void createNote(String title, String description, String userIdentifier)  {

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

        helperTestMethods.helperNavToNotes(this.driver);

        helperTestMethods.helperAddNewNoteClick(this.driver);

        helperTestMethods.helperSetNote(this.driver, title, description);

        helperTestMethods.helperWaitForSuccessPageAndClickContinue(this.driver);

    }

    private void editNote(String newTitle, String newDescription) {

        helperTestMethods.helperNavToNotes(this.driver);

        helperTestMethods.helperEditNoteClick(this.driver);

        helperTestMethods.helperSetNote(this.driver, newTitle, newDescription);

        helperTestMethods.helperWaitForSuccessPageAndClickContinue(this.driver);

    }

    private void deleteNote() {

        helperTestMethods.helperNavToNotes(this.driver);

        helperTestMethods.helperDeleteNoteClick(this.driver);

        helperTestMethods.helperWaitForSuccessPageAndClickContinue(this.driver);
    }


    /**
     * Write a Selenium test that logs in an existing user,
     * creates a note and verifies that the note details are
     * visible in the note list.
     */
    @Test
    public void testAddingNotes() {
        String title1 = "Title 1";
        String description1 = "Description 1";
        String userIdentifier = "_1";

        this.createNote(title1, description1, userIdentifier);

        helperTestMethods.helperNavToNotes(this.driver);

        WebDriverWait tableNoteTile = new WebDriverWait(driver, 5);
        tableNoteTile.until(ExpectedConditions.visibilityOfElementLocated(By.id("tableNoteTitle")));

        Assertions.assertEquals(title1, driver.findElement(By.id("tableNoteTitle")).getText());
        Assertions.assertEquals(description1, driver.findElement(By.id("tableNoteDescription")).getText());
    }

    /**
     * Write a Selenium test that logs in an existing user with existing notes,
     * clicks the edit note button on an existing note,
     * changes the note data, saves the changes, and verifies that the changes
     * appear in the note list.
     */

    @Test
    public void testEditingANote() {
        String title1 = "Title 1";
        String title2 = "Title 2";
        String description1 = "Description 1";
        String description2 = "Description 2";
        String userIdentifier = "_2";


        this.createNote(title1, description1, userIdentifier);

        helperTestMethods.helperNavToNotes(this.driver);

        WebDriverWait tableNoteTile = new WebDriverWait(driver, 5);
        tableNoteTile.until(ExpectedConditions.visibilityOfElementLocated(By.id("tableNoteTitle")));

        Assertions.assertEquals(title1, driver.findElement(By.id("tableNoteTitle")).getText());
        Assertions.assertEquals(description1, driver.findElement(By.id("tableNoteDescription")).getText());

        helperTestMethods.helperLogOut(driver);

        helperTestMethods.helperLogIn(
        helperTestMethods.USERNAME + userIdentifier,
        helperTestMethods.PASSWORD + userIdentifier,
            this.driver,
            this.port
        );

        this.editNote(title2, description2);

        helperTestMethods.helperNavToNotes(this.driver);

        WebDriverWait tableNoteTile2 = new WebDriverWait(driver, 5);
        tableNoteTile2.until(ExpectedConditions.visibilityOfElementLocated(By.id("tableNoteTitle")));

        Assertions.assertEquals(title2, driver.findElement(By.id("tableNoteTitle")).getText());
        Assertions.assertEquals(description2, driver.findElement(By.id("tableNoteDescription")).getText());
    }


    /**
     * Write a Selenium test that logs in an existing user
     * with existing notes, clicks the delete note button on
     * an existing note, and verifies that the note no longer
     * appears in the note list.
     */
    @Test
    public void testDeletingANote() {
        String title1 = "Title 1";
        String description1 = "Description 1";
        String userIdentifier = "_3";

        this.createNote(title1, description1, userIdentifier);

        helperTestMethods.helperNavToNotes(this.driver);

        WebDriverWait tableNoteTile = new WebDriverWait(driver, 5);
        tableNoteTile.until(ExpectedConditions.visibilityOfElementLocated(By.id("tableNoteTitle")));

        Assertions.assertEquals(title1, driver.findElement(By.id("tableNoteTitle")).getText());
        Assertions.assertEquals(description1, driver.findElement(By.id("tableNoteDescription")).getText());

        helperTestMethods.helperLogOut(driver);

        helperTestMethods.helperLogIn(
                helperTestMethods.USERNAME + userIdentifier,
                helperTestMethods.PASSWORD + userIdentifier,
                this.driver,
                this.port
        );

        this.deleteNote();

        helperTestMethods.helperNavToNotes(this.driver);

        Assertions.assertFalse(helperTestMethods.helperDoesElementExist(this.driver, "tableNoteTitle"));
    }
}
