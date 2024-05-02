package com.kavin;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AppTest {

    WebDriver driver;
    Workbook workbook;
    Sheet sheet;
    Row row;
    String location;
    Logger logger;

    @BeforeSuite
    public void extractDetails() throws IOException {
        logger = LogManager.getLogger(getClass());
        try {
            workbook = new XSSFWorkbook("sheets/values.xlsx");
            sheet = workbook.getSheetAt(0);
            row = sheet.getRow(1);
            location = row.getCell(0).getStringCellValue();
            logger.info("Values extracted from excel sheet successfully");
        } catch (Exception e) {
            logger.warn("There is a problem in  extracting values from excel sheet successfully");

        }

    }

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
    }

    @Test(priority = 0)
    public void testCase1() throws InterruptedException, IOException {

        driver.manage().window().maximize();
        driver.get("https://www.opentable.com");
        logger.info("Url opened successfully");

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"mainContent\"]/header/div/span/div/div/div[2]/div[1]/div/input"))
                .sendKeys(location);
        logger.info("Data entered into the location input field");

        driver.findElement(By.xpath("//*[@id=\"mainContent\"]/header/div/span/div/div/div[2]/div[2]/button"))
                .click();

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/section/div[6]/div/label[4]/span[2]"))
                .click();
        logger.info("Location search successfull");

        Thread.sleep(2000);
        driver.findElement(By.partialLinkText("Far & East")).click();
        logger.info("Far and East hotel clicked");

        Thread.sleep(2000);
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            String path = "./screenshots/snap.png";
            FileUtils.copyFile(source, new File(path));
            logger.info("Screenshot took successfully");
        } catch (Exception e) {
            logger.warn("Screenshot not taken");
        }
        String currWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            if (!currWindow.equals(window)) {
                driver.switchTo().window(window);
                logger.info("Tab switch was successfull");
                break;
            }
        }
        Thread.sleep(4000);
        Select partySize = new Select(
                driver.findElement(By.xpath("//*[@id=\"restProfileSideBarDtpPartySizePicker\"]")));
        partySize.selectByIndex(3);
        logger.info("Number of people was selected");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"restProfileSideBarDtpDayPicker\"]/div/div/span")).click();
        Thread.sleep(4000);
        while (true) {
            String curr = driver
                    .findElement(By.xpath(
                            "//*[@id=\"restProfileSideBarDtpDayPicker-wrapper\"]/div/div/div/div"))
                    .getText();
            if (curr.equals("November 2024")) {
                driver.findElement(By.xpath(
                        "//*[@id=\"restProfileSideBarDtpDayPicker-wrapper\"]/div/div/div/table/tbody/tr[3]/td[2]/button"))
                        .click();
                break;
            }
            driver.findElement(
                    By.xpath("//*[@id=\"restProfileSideBarDtpDayPicker-wrapper\"]/div/div/div/div/div[2]/button[2]"))
                    .click();
        }
        Select time = new Select(
                driver.findElement(By.xpath("//*[@id=\"restProfileSideBartimePickerDtpPicker\"]")));
        time.selectByVisibleText("6:30 PM");
        Thread.sleep(2000);
        logger.info("Date and time was selected");
        driver.findElement(By.xpath("//*[@id=\"baseApp\"]/div/header/div[2]/div[2]/div[1]/button")).click();
        Thread.sleep(5000);
        logger.info("Sign in button clicked");

    }

    @AfterTest
    public void quit() {
        // driver.quit();
    }
}