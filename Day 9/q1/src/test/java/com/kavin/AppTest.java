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
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class AppTest {
    String val1, val2, val3;
    Workbook workbook;
    Sheet sheet;
    Row row;
    ExtentReports reports;
    ExtentSparkReporter spark;
    ExtentTest test;
    String URL = "https://groww.in/";
    WebDriver webDriver;
    Wait<WebDriver> wait;
    Actions actions;

    @BeforeSuite
    public void init() {
        webDriver = new ChromeDriver();
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        reports = new ExtentReports();
    }

    @BeforeTest
    public void getReport() {
        spark = new ExtentSparkReporter("./reports/report.html");
        reports.attachReporter(spark);
        spark.config().setDocumentTitle("Reports");
        spark.config().setTheme(Theme.DARK);
    }

    @BeforeTest
    public void getValuesFromExcel() throws IOException {
        test = reports.createTest("Test Extracting values from Excel file");
        try {
            workbook = new XSSFWorkbook("./files/values.xlsx");
            sheet = workbook.getSheetAt(0);
            row = sheet.getRow(1);
            val1 = row.getCell(0).getNumericCellValue() + "";
            val2 = row.getCell(1).getNumericCellValue() + "";
            val3 = row.getCell(2).getNumericCellValue() + "";
            workbook.close();
            test.log(Status.PASS, "Values extracted successfully");

        } catch (Exception e) {
            test.log(Status.FAIL, "Values extraction failed");
        }
    }

    @Test
    public void navigateToPage() {
        test = reports.createTest("Test Page navigation");
        try {
            webDriver.manage().window().maximize();
            webDriver.get(URL);
            test.log(Status.PASS, "Page navigation success");

        } catch (Exception e) {
            test.log(Status.FAIL, "Page navigation failed");
        }
    }

    @Test
    public void testCalculator() {
        test = reports.createTest("Test calculator");
        try {
            WebElement calc = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Calculators")));
            calc.click();
            test.log(Status.PASS, "Calculator link clicked");

            WebElement head = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div[2]/h1")));

            if (head.getText().equals("Calculators")) {
                test.log(Status.PASS, "Calculator text is found");
                try {
                    TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
                    File file = takesScreenshot.getScreenshotAs(OutputType.FILE);
                    FileUtils.copyFile(file, new File("./screenshots/screenshot.png"));
                    test.log(Status.PASS, "Screenshot captured and saved successfully");
                } catch (Exception e) {
                    test.log(Status.PASS, "Screenshot capture failed");
                }

            } else {
                test.log(Status.FAIL, "There is no calculator text");
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "There is some problem in calculator link");
        }
    }

    @Test
    public void testHomeLoan() throws InterruptedException {
        test = reports.createTest("Test Home loan");

        WebElement loan = wait.until(d -> d.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[2]/a[15]")));
        // actions.scrollToElement(loan).perform();
        try {
            loan.click();
            test.log(Status.PASS, "Home loan clicked");

        } catch (Exception e) {
            test.log(Status.FAIL, "Home loan not clicked");
        }
    }

    @Test
    public void testHomeLoanCalculator() {
        test = reports.createTest("Test Home loan calculator");
        WebElement amt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("LOAN_AMOUNT")));
        try {
            amt.clear();
            amt.sendKeys(val1);

            amt = webDriver.findElement(By.id("RATE_OF_INTEREST"));
            amt.clear();
            amt.sendKeys(val2);

            amt = webDriver.findElement(By.id("LOAN_TENURE"));
            amt.clear();
            amt.sendKeys(val3);

            test.log(Status.PASS, "Values passed successfully");

            try {
                WebElement h = wait.until(d -> d.findElement(
                        By.xpath("/html/body/div/div[2]/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div/p")));
                if (h.getText().equals("Your Amortization Details (Yearly/Monthly)")) {
                    test.log(Status.PASS, "Your Amortization Details (Yearly/Monthly) is present");
                } else {
                    throw new Exception();
                }

            } catch (Exception e) {
                test.log(Status.FAIL, "Your Amortization Details (Yearly/Monthly) is not present");
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "Home loan calculator values are not FAILed");
        }
    }

    @AfterTest
    public void destruct() {
        reports.flush();
        webDriver.quit();
    }
}
