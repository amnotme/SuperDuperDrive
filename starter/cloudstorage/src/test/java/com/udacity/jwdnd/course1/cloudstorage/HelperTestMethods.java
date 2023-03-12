package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class HelperTestMethods {


    public final String LOCALHOST = "http://localhost:";

    public final String LOGIN_T = "Login";

    public final String NAME = "Leo";

    public final String LASTNAME = "Hernandez";

    public final String USERNAME = "lhernandez";

    public final String PASSWORD = "This*is*0bviously*not*my*passw0rd!";

    public void helperSignUp(String firstName, String lastName, String userName, String password, WebDriver driver, int port) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

    }

    public void helperLogIn(String userName, String password, WebDriver driver, int port) {
        driver.get("http://localhost:" + port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }

    public void helperLogOut(WebDriver driver) {
        WebElement logOutButton = driver.findElement(By.id("logOutButton"));
        logOutButton.click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.titleContains("Login"));
    }



    public void helperNavToNotes(WebDriver driver) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));

        WebElement navNotesTab = driver.findElement(By.id("nav-notes-tab"));
        navNotesTab.click();
    }

    public void helperNavToCred(WebDriver driver) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

        WebElement navNotesTab = driver.findElement(By.id("nav-credentials-tab"));
        navNotesTab.click();
    }

    public void helperAddNewNoteClick(WebDriver driver) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNewNote")));

        WebElement addNewNote = driver.findElement(By.id("addNewNote"));
        addNewNote.click();
    }

    public void helperAddNewCredClick(WebDriver driver) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNewCred")));

        WebElement addNewCred = driver.findElement(By.id("addNewCred"));
        addNewCred.click();
    }
    public void helperEditNoteClick(WebDriver driver) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editNote")));

        WebElement editNote = driver.findElement(By.id("editNote"));
        editNote.click();
    }

    public void helperEditCredClick(WebDriver driver) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editCred")));

        WebElement editCred = driver.findElement(By.id("editCred"));
        editCred.click();
    }

    public void helperDeleteNoteClick(WebDriver driver) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteNote")));

        WebElement deleteNoteClick = driver.findElement(By.id("deleteNote"));
        deleteNoteClick.click();
    }
    public void helperDeleteCredClick(WebDriver driver) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCred")));

        WebElement deleteCredClick = driver.findElement(By.id("deleteCred"));
        deleteCredClick.click();
    }


    public void helperSetNote(WebDriver driver, String title, String description) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitle = driver.findElement(By.id("note-title"));

        noteTitle.click();
        noteTitle.clear();
        noteTitle.sendKeys(title);


        WebElement noteDescription = driver.findElement(By.id("note-description"));
        noteDescription.click();
        noteDescription.clear();
        noteDescription.sendKeys(description);

        WebElement noteSubmitButton = driver.findElement(By.id("noteSubmitbutton"));
        noteSubmitButton.click();
    }

    public void helperSetCred(WebDriver driver, String url, String username, String password) {
        WebDriverWait credUrlWait = new WebDriverWait(driver, 5);
        credUrlWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement credUrl = driver.findElement(By.id("credential-url"));

        credUrl.click();
        credUrl.clear();
        credUrl.sendKeys(url);


        WebElement credUsername = driver.findElement(By.id("credential-username"));
        credUsername.click();
        credUsername.clear();
        credUsername.sendKeys(username);

        WebElement credPassword = driver.findElement(By.id("credential-password"));
        credPassword.click();
        credPassword.clear();
        credPassword.sendKeys(password);

        WebElement noteSubmitButton = driver.findElement(By.id("credSubmitButton"));
        noteSubmitButton.click();
    }

    public void helperWaitForSuccessPageAndClickContinue(WebDriver driver) {
        WebDriverWait webDriverWaitSuccessPage = new WebDriverWait(driver, 2);
        webDriverWaitSuccessPage.until(ExpectedConditions.titleContains("Result"));

        driver.findElement(By.id("resultSuccess")).click();
    }

    public boolean helperDoesElementExist(WebDriver driver, String key) {
        return (driver.findElements(By.id(key))).size() > 0;
    }
}
