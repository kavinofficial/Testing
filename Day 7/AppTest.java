package com.skcet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    WebDriver driver;
    Actions actions;

    @BeforeTest
    public void beforeTest()
    {
        driver = new ChromeDriver();
        actions = new Actions(driver);
    }

    @Test
    public void testingEconomicTimes() throws Exception
    {
        driver.get("https://economictimes.indiatimes.com/et-now/results");
        Thread.sleep(10000);

        // move to mutual funds
        driver.findElement(By.xpath("//nav[@id='topnav']/div[@data-ga-action='Mutual Funds']/a")).click();
        Thread.sleep(10000);

        WebElement select = driver.findElement(By.id("amcSelection"));
        actions.scrollToElement(select).perform();

        // select.click();
        // driver.findElement(By.xpath("//select[@id='amcSelection']/option[@value='8']")).click();

        Select selectEle = new Select(select);
        selectEle.selectByVisibleText("Canara Robeco");
        Thread.sleep(3000);

        select = driver.findElement(By.id("schemenm"));

        // select.click();
        // driver.findElement(By.xpath("//select[@id='schemenm']/option[@value='15765']")).click();

        selectEle = new Select(select);
        selectEle.selectByVisibleText("Canara Robeco Bluechip Equity Direct-G");
        Thread.sleep(3000);

        driver.findElement(By.xpath("//*[@id='getDetails']")).click();
        Thread.sleep(5000);

        // the above actions will create a new tab
        String current_tab = driver.getWindowHandle(); // current tab
        for (String s : driver.getWindowHandles()) {
            if (!s.equals(current_tab)) {
                // switches to newly opened tab
                driver.switchTo().window(s);
            }
        }

        // set amount as 1000
        driver.findElement(By.xpath("//*[@id='installment_amt']/li/span")).click();
        driver.findElement(By.xpath("//*[@id='installment_amt']/li/ul/li[3]")).click();

        // set period as 3 years
        driver.findElement(By.xpath("//*[@id='installment_period']/li/span")).click();
        driver.findElement(By.xpath("//*[@id='installment_period']/li/ul/li[4]/span")).click();

        // navigate to returns
        driver.findElement(By.xpath("//*[@id=\"mfNav\"]/div/ul/li[2]")).click();
        WebElement details = driver
                .findElement(By.xpath("//*[@id='mfReturns']/div[2]/div[2]/ul/li[1]/table/tbody/tr[1]"));

        System.out.println(details.getText());
    }
    
    @AfterTest
    public void afterTest()
    {
        driver.quit();
    }

    // @Test(priority = 0)
    // public void openWebpage() throws InterruptedException
    // {
    //     driver.get("https://economictimes.indiatimes.com/et-now/results");
    //     Thread.sleep(10000);
    //     driver.findElement(By.xpath("//nav[@id='topnav']/div[@data-ga-action='Mutual Funds']/a")).click();
    //     Thread.sleep(10000);
    // }

    // @Test(priority = 2)
    // public void selectAMC() throws Exception
    // {
    //     WebElement selectEle;

    //     selectEle = driver.findElement(By.id("amcSelection"));
    //     actions.scrollToElement(selectEle).perform();
    //     Select select = new Select(selectEle);
    //     select.selectByVisibleText("Canara Robeco");
    //     Thread.sleep(3000);

    //     selectEle = driver.findElement(By.id("schemenm"));
    //     select = new Select(selectEle);
    //     select.selectByVisibleText("Canara Robeco Bluechip Equity Direct-G");
    //     Thread.sleep(3000);
    // }

    // @Test(priority = 3)
    // public void gotoNewTab() throws Exception
    // {
    //     String current_tab = driver.getWindowHandle(); // current tab

    //     for (String s : driver.getWindowHandles()) {
    //         if (!s.equals(current_tab)) {
    //             // switches to newly opened tab
    //             driver.switchTo().window(s);
    //         }
    //     }
    // }

    // @Test(priority = 4)
    // public void setAmount() throws Exception
    // {
    //     driver.findElement(By.xpath("//*[@id='installment_amt']/li/span")).click();
    //     driver.findElement(By.xpath("//*[@id='installment_amt']/li/ul/li[3]")).click();
    // }

    // @Test(priority = 5)
    // public void setPeriod() throws Exception
    // {
    //     driver.findElement(By.xpath("//*[@id='installment_period']/li/span")).click();
    //     driver.findElement(By.xpath("//*[@id='installment_period']/li/ul/li[4]/span")).click();
    // }

    // @Test(priority = 6)
    // public void navigateToReturns() throws Exception
    // {
    //     driver.findElement(By.xpath("//*[@id=\"mfNav\"]/div/ul/li[2]")).click();
    //     WebElement details = driver
    //             .findElement(By.xpath("//*[@id='mfReturns']/div[2]/div[2]/ul/li[1]/table/tbody/tr[1]"));

    //     System.out.println(details.getText());
    //     Thread.sleep(10000);
    // }
}
