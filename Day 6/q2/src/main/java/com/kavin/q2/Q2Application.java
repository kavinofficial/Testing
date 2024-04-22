package com.kavin.q2;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Q2Application {

	public static void main(String[] args) {
		WebDriver webDriver = new ChromeDriver();
		Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
		webDriver.get("https://www.moneycontrol.com/");

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
				webDriver.findElement(By.id("google_ads_iframe_/21751243814,1039154/385208-970-90-1_0"))));
		// WebElement frame =
		// webDriver.findElement(By.id("google_ads_iframe_/21751243814,1039154/385208-970-90-1_0"));

	}

}
