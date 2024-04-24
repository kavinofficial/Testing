package com.kavin;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
    String URL = "https://www.firstcry.com/";
    String value1, value2, value3;
    WebDriver webDriver;
    Wait<WebDriver> wait;
    Actions actions;

    @BeforeTest
    public void init() throws IOException {
        webDriver = new ChromeDriver();
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        actions = new Actions(webDriver);
        Workbook workbook = new XSSFWorkbook("D:\\Testing\\Day 8\\h1\\files\\values.xlsx");
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1);
        value1 = row.getCell(0).getStringCellValue();
        row = sheet.getRow(2);
        value2 = row.getCell(0).getStringCellValue();
        row = sheet.getRow(3);
        value3 = row.getCell(0).getStringCellValue();
        workbook.close();
    }

    @Test
    public void Test1() throws InterruptedException {
        webDriver.manage().window().maximize();
        webDriver.get(URL);
        WebElement search = webDriver.findElement(By.id("search_box"));
        search.clear();
        search.sendKeys(value1);
        search.sendKeys(Keys.ENTER);
        if (webDriver.getCurrentUrl().contains("kids-toys")) {
            System.out.println("URL verified");
        } else {
            System.out.println("URL invalid");
        }
    }

    @Test
    public void Test2() throws InterruptedException {
        webDriver.manage().window().maximize();
        webDriver.get(URL);
        WebElement search = webDriver.findElement(By.id("search_box"));
        search.clear();
        search.sendKeys(value2);
        search.sendKeys(Keys.ENTER);
        WebElement check = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[5]/div[2]/div/div[1]/div[3]/div[2]/div[1]/div[2]/div[2]/ul/li[4]/a/span")));
        check.click();

        WebElement heading = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("/html/body/div[5]/div[2]/div/div[2]/div[1]/div[1]/h1")));

        if (heading.getText().equals("Ethnic Wear")) {
            System.out.println("Presence verified");
        } else {
            System.out.println("Presence invalid");
        }
    }

    @Test
    public void Test3() throws InterruptedException {
        webDriver.manage().window().maximize();
        webDriver.get(URL);
        WebElement search = webDriver.findElement(By.id("search_box"));
        search.clear();
        search.sendKeys(value3);
        search.sendKeys(Keys.ENTER);

        WebElement first = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[5]/div[2]/div/div[2]/div[3]/div[1]/div[2]/div[1]/div/div[1]/div[2]/a")));
        String firstName = first.getText();
        first.click();

        // TAB SWITCH
        for (String handle : webDriver.getWindowHandles()) {
            if (!handle.equals(webDriver.getWindowHandle())) {
                webDriver.switchTo().window(handle);
                break;
            }
        }

        WebElement next = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("prod_name")));
        if (firstName.equals(next.getText())) {
            System.out.println("Product value matched");
        } else {
            System.out.println("Product value mismatched");
        }
    }

    @AfterTest
    public void destruct() {
        webDriver.quit();
    }
}
