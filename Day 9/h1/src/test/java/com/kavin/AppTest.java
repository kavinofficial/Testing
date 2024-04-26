package com.kavin;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    String URL = "https://www.google.com/";
    WebDriver webDriver;
    Wait<WebDriver> wait;

    @BeforeTest
    public void init() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        webDriver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
    }

    @Test
    public void test1() throws InterruptedException, IOException {
        webDriver.manage().window().maximize();
        webDriver.get(URL);

        // train
        WebElement train = webDriver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/div/div/div[2]/a"));
        wait.until(ExpectedConditions.elementToBeClickable(train)); // wait until the element is clickable
        train.click();
        System.out.println("Clicked");

        // Assert.assertTrue(webDriver.getCurrentUrl().contains("trains"));

        // WebElement back = wait
        // .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[1]/div/div[1]/a")));
        // back.click();

        // // login
        // WebElement login = wait.until(
        // ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/header/div/div[3]/div/nav/a[3]")));
        // login.click();

        // WebElement checkHead =
        // wait.until(ExpectedConditions.visibilityOfElementLocated(
        // By.xpath("/html/body/div[2]/div[2]/div/div/div[2]/div/div[1]/div/div/div[1]/h4")));

        // Assert.assertTrue(checkHead.getText().equals("Login to Abhibus"));
        // TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;

        // File file = takesScreenshot.getScreenshotAs(OutputType.FILE);

        // FileUtils.copyFile(file, new File("./screenshots/screenshot.png"));

    }
}
