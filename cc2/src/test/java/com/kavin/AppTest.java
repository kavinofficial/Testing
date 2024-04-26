package com.kavin;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

/**
 * Unit test for simple App.
 */
public class AppTest {
    String URL = "https://www.barnesandnoble.com/";
    String excelPath = "./files/values.xlsx";
    WebDriver webDriver;
    Wait<WebDriver> wait;
    Workbook workbook;
    Sheet sheet;
    Row row;
    String searchvalue;
    Actions actions;
    ExtentReports reports;
    ExtentSparkReporter spark;
    ExtentTest test;
    Logger logger;
    TakesScreenshot takesScreenshot;
    File file;

    @BeforeSuite
    public void extractFromExcel() throws IOException {
        workbook = new XSSFWorkbook(excelPath);
        sheet = workbook.getSheetAt(0);
        row = sheet.getRow(1);
        searchvalue = row.getCell(0).getStringCellValue();
    }

    @BeforeTest
    public void init() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        webDriver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        actions = new Actions(webDriver);
        logger = LogManager.getLogger(getClass());
        takesScreenshot = (TakesScreenshot) webDriver;

    }

    @BeforeTest
    public void setReport() {
        spark = new ExtentSparkReporter("./reports/report.html");
        reports = new ExtentReports();
        reports.attachReporter(spark);
        spark.config().setDocumentTitle("Reports");
        spark.config().setTheme(Theme.DARK);
    }

    @Test(enabled = true)
    public void testcase1() throws InterruptedException, IOException {
        webDriver.manage().window().maximize();
        webDriver.get(URL);
        logger.info("barnesandnoble WebPage opened");
        test = reports.createTest("Testcase 1");
        try {
            WebElement all = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("/html/body/div[2]/header/nav/div/div[3]/form/div/div[1]")));
            all.click();
            WebElement book = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.linkText("Books")));
            book.click();

            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("/html/body/div[2]/header/nav/div/div[3]/form/div/div[2]/div/input[1]")));
            searchBox.sendKeys(searchvalue);

            webDriver.findElement(By.xpath("/html/body/div[2]/header/nav/div/div[3]/form/div/span/button")).click();

            // search results
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                    "/html/body/main/div[2]/div[1]/div[3]/div[2]/div/div/section[1]/section[1]/div/div[1]/div[1]/h1/span")));

            if (heading.getText().equals(searchvalue)) {
                System.out.println("Contains");
                test.log(Status.PASS, "It contains the search value");
                logger.info("It contains the search value");
            }
            test.log(Status.PASS, "Testcase 1 passed");
        } catch (Exception e) {
            file = takesScreenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(file, new File("./screenshots/screenshot.png"));
            test.log(Status.FAIL, "Testcase 1 failed");
        }

    }

    @Test(enabled = true)
    public void testcase2() throws InterruptedException, IOException {
        test = reports.createTest("Testcase 2");
        webDriver.manage().window().maximize();
        webDriver.get(URL);
        try {
            wait.until(d -> d.findElement(By.xpath("/html/body/div[2]/header/nav/div/div[4]/div/ul/li[5]")));

            actions.moveToElement(
                    webDriver.findElement(By.xpath("/html/body/div[2]/header/nav/div/div[4]/div/ul/li[5]")))
                    .perform();

            WebElement top100Books = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                    "/html/body/div[2]/header/nav/div/div[4]/div/ul/li[5]/div/div/div[1]/div/div[2]/div[1]/dd/a[1]")));
            top100Books.click();

            Thread.sleep(2000);
            WebElement addToCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                    "/html/body/main/div[2]/div[1]/div/div[2]/div/div[2]/section[2]/ol/li[1]/div/div[2]/div[5]/div[2]/div/div/form/input[11]")));

            addToCart.submit();
            try {
                String find = webDriver.switchTo().alert().getText();
                if (find.contains("Item Successfully Added To Your Cart")) {
                    logger.info("Successfully inserted into the cart");
                    test.log(Status.PASS, "Successfully inserted into the cart");
                } else {
                    logger.info("Item not inserted into the cart");
                    test.log(Status.PASS, "Item not inserted into the cart");
                    FileUtils.copyFile(file, new File("./screenshots/error(Item not added to cart).png"));
                }
            } catch (Exception e) {
                logger.info("Alert not present");
                test.log(Status.FAIL, "Alert not present");
                file = takesScreenshot.getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(file, new File("./screenshots/screenshot.png"));
            }
            test.log(Status.PASS, "Testcase 2 passed");
        } catch (Exception e) {
            file = takesScreenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(file, new File("./screenshots/screenshot.png"));
            test.log(Status.FAIL, "Testcase 2 failed");
        }
    }

    @Test(enabled = true)
    public void test3() throws InterruptedException, IOException {
        webDriver.manage().window().maximize();
        webDriver.get(URL);
        test = reports.createTest("Testcase 3");
        try {
            WebElement scroll = wait.until(
                    ExpectedConditions
                            .visibilityOfElementLocated(By.xpath("/html/body/main/div[3]/section[1]/div[1]/h2")));
            actions.moveToElement(scroll).perform();
            Thread.sleep(2000);
            WebElement bn = wait
                    .until(ExpectedConditions
                            .elementToBeClickable(
                                    By.xpath("/html/body/main/div[3]/div[3]/div/section/div/div/div/a[1]")));
            bn.click();

            WebElement join = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rewards-modal-link")));
            join.click();
            test.log(Status.PASS, "Testcase 3 passed");
            logger.info("Testcase 3 passed");
        } catch (Exception e) {
            file = takesScreenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(file, new File("./screenshots/screenshot.png"));
            test.log(Status.FAIL, "Testcase 3 failed");
            logger.info("Testcase 3 failed");
        }

    }

    @AfterTest
    public void destruct() {
        reports.flush();
        webDriver.quit();
    }
}
