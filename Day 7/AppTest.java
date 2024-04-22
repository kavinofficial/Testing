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
}
