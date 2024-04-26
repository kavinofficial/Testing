package com.kavin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class AppTest {

    WebDriver driver;
    Actions actions;
    ExtentReports reports;
    Wait<WebDriver> wait;

    TripDetails trip;
    SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy");

    class TripDetails {

        String from, to, classSection;
        Date departureDate, returnDate;
        int noOfAdults, noOfChildren, noOfInfants;

        TripDetails(Row row) {
            // String Values
            this.from = row.getCell(0).getStringCellValue();
            this.to = row.getCell(1).getStringCellValue();
            this.classSection = row.getCell(7).getStringCellValue();

            // Date Values
            this.departureDate = row.getCell(2).getDateCellValue();
            this.returnDate = row.getCell(3).getDateCellValue();

            // Numerical values
            this.noOfAdults = (int) row.getCell(4).getNumericCellValue();
            this.noOfChildren = (int) row.getCell(5).getNumericCellValue();
            this.noOfInfants = (int) row.getCell(6).getNumericCellValue();

            System.out.println(this);
        }

        @Override
        public String toString() {
            return "From           : " + from + "\n" +
                    "To             : " + to + "\n" +
                    "Departure      : " + sdf.format(departureDate) + "\n" +
                    "Return         : " + sdf.format(returnDate) + "\n" +
                    "No of Adults   : " + noOfAdults + "\n" +
                    "No of Children : " + noOfChildren + "\n" +
                    "No of Infants  : " + noOfInfants + "\n" +
                    "Class Section  : " + classSection + "\n";
        }
    }

    @BeforeTest
    public void setupDriver() {
        this.driver = new ChromeDriver();
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @BeforeTest
    public void setupExcel() throws Exception {
        Workbook workbook = new XSSFWorkbook("./files/values.xlsx");
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1);

        this.trip = new TripDetails(row);
        workbook.close();
    }

    @BeforeTest
    public void setupReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("./reports/report.html");
        this.reports = new ExtentReports();
        this.reports.attachReporter(spark);
        spark.config().setDocumentTitle("ixigo website Report");
        spark.config().setTheme(Theme.DARK);
    }

    /**
     * Testcase 1
     */

    @Test(priority = 1)
    public void testIxigoWebsite() throws Exception {
        driver.get("https://www.ixigo.com/");

        // Step 1
        WebElement roundTripButton = wait.until(
                d -> d.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[1]/div[1]/div/button[2]")));
        roundTripButton.click();

        // Step 2
        WebElement inputFromDiv = wait.until(
                d -> d.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div")));
        inputFromDiv.click();

        WebElement inputFrom = wait.until(d -> d.findElement(
                By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[2]/div/div/div[2]/input")));
        inputFrom.sendKeys(trip.from);
        Thread.sleep(2000);

        // Step 3
        By firstOptionSelector = By.className("Autocompleter_animate__zqRDe");
        wait.until(d -> ExpectedConditions.visibilityOfElementLocated(firstOptionSelector));
        WebElement firstOption = driver
                .findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[3]/div[1]/li"));
        firstOption.click();
        Thread.sleep(2000);

        // Step 4
        WebElement inputToDiv = wait.until(
                d -> d.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[1]/div")));
        inputToDiv.click();

        // Step 5
        WebElement inputTo = wait.until(d -> d.findElement(
                By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div[2]/input")));
        inputTo.sendKeys(trip.to);

        // Step 5
        By firstOptionToSelector = By.className("Autocompleter_animate__zqRDe");
        wait.until(d -> ExpectedConditions.visibilityOfElementLocated(firstOptionToSelector));

        Thread.sleep(3000);

        WebElement firstToOption = wait.until(d -> d
                .findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[3]/div[1]/li")));
        firstToOption.click();

        // Step 6
        WebElement departure = wait.until(d -> d
                .findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[1]/div/div")));
        departure.click();

        Thread.sleep(5000);

        // Step 7
        By departureSelector = By.cssSelector("[aria-label=\"" + sdf.format(trip.departureDate) + "\"]");
        WebElement departureInput = wait.until(d -> d.findElement(departureSelector));
        ((JavascriptExecutor) driver).executeScript("arguments[0].parentElement.click();", departureInput);

        Thread.sleep(5000);

        // Step 8
        By returnSelector = By.cssSelector("[aria-label=\"" + sdf.format(trip.returnDate) + "\"]");
        WebElement returnInput = wait.until(d -> d.findElement(returnSelector));
        ((JavascriptExecutor) driver).executeScript("arguments[0].parentElement.click();", returnInput);

        // Step 9
        WebElement travelClass = wait
                .until(d -> d.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[1]/div")));
        travelClass.click();

        Thread.sleep(5000);

        // Step 10
        // Increment Adult by 1
        driver.findElement(By.xpath(
                "/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[1]/div[1]/div[2]/div/button["
                        + trip.noOfAdults + "]"))
                .click();

        // Increment Child by 1
        driver.findElement(By.xpath(
                "/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[1]/div[2]/div[2]/div/button["
                        + trip.noOfChildren + "]"))
                .click();

        // Select Class option
        WebElement classDiv = driver.findElement(
                By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[1]/div[5]/div"));
        List<WebElement> classButtons = classDiv.findElements(By.tagName("div"));

        // Click the Class option
        String[] classes = { "Economy", "Premium Economy", "Business" };
        for (int i = 0; i < classes.length; i++) {
            if (trip.classSection.equals(classes[i])) {
                classButtons.get(i).click();
            }
        }

        // Click on the Done button
        driver.findElement(By.xpath(
                "/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[2]/button"))
                .click();

        Thread.sleep(5000);

        // Step 9
        WebElement returnDate = driver.findElement(By.cssSelector("[data-testid=\"returnDate\"]"));
        ExtentTest datePickerTest = reports.createTest("VerifyDatePicker");

        if (returnDate.getText().contains("08 May")) {
            datePickerTest.log(Status.PASS, "Date is present in the return date picker");
        } else {
            datePickerTest.log(Status.FAIL, "Date is not present in the return date picker");
        }

        takeScreenshot("./src/screenshots/datepicker.png");

        Thread.sleep(5_000);
    }

    /**
     * Testcase 2
     */

    @Test(priority = 2)
    public void testAboutUs() throws IOException {
        // Step 1
        driver.get("https://www.ixigo.com/");

        // Step 2
        WebElement aboutSection = wait.until(d -> d.findElement(By.linkText("About Us")));
        actions.scrollToElement(aboutSection);

        // Step 3
        aboutSection.click();

        // Switch to the new window
        Set<String> handles = driver.getWindowHandles();
        String currentTab = driver.getWindowHandle();
        for (String handle : handles) {
            if (!handle.equals(currentTab)) {
                driver.switchTo().window(handle);
            }
        }

        ExtentTest verifyAboutURL = reports.createTest("VerifyAboutURL");

        if (driver.getCurrentUrl().contains("about")) {
            verifyAboutURL.log(Status.PASS, "URL contains about");
        } else {
            verifyAboutURL.log(Status.FAIL, "URL does not contain about");
        }

        takeScreenshot("./screenshots/about.png");
    }

    @AfterTest
    public void wrapUp() {
        driver.quit();
        reports.flush();
    }

    private void takeScreenshot(String filename) throws IOException {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        FileUtils.copyFile(screenshotFile, new File(filename + "_" + timestamp + ".png"));
    }
}