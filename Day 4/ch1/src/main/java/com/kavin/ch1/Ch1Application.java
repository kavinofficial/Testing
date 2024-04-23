package com.kavin.ch1;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Ch1Application {

	public static void main(String[] args) {
		String URL = "https://www.shoppersstop.com/";
		WebDriver webDriver = new ChromeDriver();
		webDriver.get(URL);
		Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("/html/body/main/header/div[1]/div/div[2]/div[2]/ul/li[4]/a/i")));

		webDriver.findElement(By.xpath("/html/body/main/header/div[1]/div/div[2]/div[2]/ul/li[4]/a/i")).click();

		webDriver.manage().window().maximize();

		System.out.println("Title :" + webDriver.getTitle());
		String source = webDriver.getPageSource();
		System.out.println("Length :" + source.length());

		webDriver.navigate().to("https://www.google.com");
		webDriver.navigate().back();

		if (webDriver.getCurrentUrl().equals(URL)) {
			System.out.println("Matches with the url");
		} else {
			System.out.println("Does not match with the url");
		}
		webDriver.quit();
	}

}
