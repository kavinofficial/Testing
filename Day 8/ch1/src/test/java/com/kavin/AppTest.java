package com.kavin;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    String value1, value2;
    WebDriver webDriver;
    Wait<WebDriver> wait;
    Actions actions;
    String URL = "https://magento.softwaretestingboard.com/";

    @BeforeTest
    public void init() throws IOException {
        webDriver = new ChromeDriver();
        actions = new Actions(webDriver);
        Workbook workbook = new XSSFWorkbook("D:\\Testing\\Day 8\\ch1\\files\\values.xlsx");
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1);
        value1 = row.getCell(0).getStringCellValue();
        row = sheet.getRow(2);
        value2 = row.getCell(0).getStringCellValue();
        workbook.close();
    }

    @Test
    public void Test1() throws IOException {
        webDriver.get(URL);

        WebElement search = webDriver.findElement(By.id("search"));
        search.sendKeys(value1);
        search.sendKeys(Keys.ENTER);

        WebElement heading = webDriver.findElement(By.xpath("/html/body/div[2]/main/div[1]/h1/span"));
        if (heading.getText().contains("Shoe")) {
            System.out.println("Text found");
            TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
            File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("screenshots/screenshot1.png"));
        } else {
            System.out.println("Text not found");
        }
    }

    @Test
    public void Test2() throws IOException {
        webDriver.get(URL);

        WebElement search = webDriver.findElement(By.id("search"));
        search.sendKeys(value2);
        search.sendKeys(Keys.ENTER);

        WebElement heading = webDriver.findElement(By.xpath("/html/body/div[2]/main/div[1]/h1/span"));
        if (heading.getText().contains("Hoodie")) {
            System.out.println("Text found");
            TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
            File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("screenshots/screenshot2.png"));
        } else {
            System.out.println("Text not found");
        }
    }

    @Test
    public void Test3() throws IOException {
        webDriver.get(URL);
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        WebElement head = webDriver
                .findElement(By.xpath("/html/body/div[2]/main/div[3]/div/div[3]/div[1]/div/a[2]/span[2]/span[2]"));
        head.click();

        WebElement cloth = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("/html/body/div[2]/main/div[3]/div[1]/div[4]/ol/li[3]")));

        actions.moveToElement(cloth).perform();

        WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("/html/body/div[2]/main/div[3]/div[1]/div[4]/ol/li[3]/div/div/div[4]/div/div[1]/form/button")));
        add.click();
        WebElement qty = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("/html/body/div[2]/main/div[2]/div/div[2]/div[4]/form/div[2]/div/div/div[1]/div/input")));
        actions.scrollToElement(qty);
        if (qty.getAttribute("value").equals("1")) {
            System.out.println("Quantity verified");
            TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
            File file = takesScreenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(file, new File("screenshots/screenshot.png"));
        } else {
            System.out.println("Quantity invalid");
        }

    }

    @AfterTest
    public void destruct() {
        webDriver.quit();
    }
}
