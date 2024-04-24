package com.kavin;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AppTest {
    WebDriver webDriver;
    Wait<WebDriver> wait;
    String username, password;
    String URL = "https://www.demoblaze.com/";

    @BeforeTest
    public void init() throws IOException {

        webDriver = new ChromeDriver();
        Workbook workbook = new XSSFWorkbook("D:\\Testing\\Day 8\\q2\\files\\credentials.xlsx");
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1);
        username = row.getCell(0).getStringCellValue();
        password = row.getCell(1).getStringCellValue();
        workbook.close();
    }

    @Test
    public void Test1() {
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        webDriver.get(URL);
        webDriver.manage().window().maximize();
        webDriver.findElement(By.linkText("Laptops")).click();
        WebElement mac = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("MacBook air")));
        mac.click();

        WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add to cart")));
        add.click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        WebElement cart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Cart")));
        cart.click();

        WebElement check = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("/html/body/div[6]/div/div[1]/div/table/tbody/tr/td[2]")));

        if (check.getText().equals("MacBook air")) {
            System.out.println("Selected product verified");
        } else {
            System.out.println("Selected product is invalid");
        }
    }

    @Test
    public void Test2() throws InterruptedException {
        webDriver.get(URL);
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        webDriver.manage().window().maximize();
        webDriver.findElement(By.linkText("Log in")).click();

        // login
        WebElement user = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
        user.sendKeys(username);
        webDriver.findElement(By.id("loginpassword")).sendKeys(password);
        webDriver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/button[2]")).click();

        WebElement profile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));
        if (profile.getText().equals("Welcome Testalpha")) {
            System.out.println("Login Confirmed");
        } else {
            System.out.println("Login Failed");
        }
    }

    @AfterTest
    public void destruct() {
        webDriver.quit();
    }
}
