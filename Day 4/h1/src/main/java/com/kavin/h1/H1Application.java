package com.kavin.h1;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class H1Application {

	public static void main(String[] args) {
		WebDriver webDriver = new ChromeDriver();
		webDriver.get("https://demowebshop.tricentis.com/");
		Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
		wait.until(d -> d.findElement(By.linkText("Jewelry")));
		webDriver.findElement(By.linkText("Jewelry")).click();

		if (webDriver.getCurrentUrl().contains("jewelry")) {
			System.out.println("Current URL contains Jewelry");
		} else {
			System.out.println("Current URL does not contain Jewelry");
		}
		webDriver.navigate().back();
		if (webDriver.getCurrentUrl().equals("https://demowebshop.tricentis.com/")) {
			System.out.println("Matches the Current URL");
		} else {
			System.out.println("Does not match the Current URL");
		}

	}

}
