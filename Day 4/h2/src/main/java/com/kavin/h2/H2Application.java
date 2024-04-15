package com.kavin.h2;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class H2Application {

	public static void main(String[] args) {
		WebDriver webDriver = new ChromeDriver();
		webDriver.get("https://www.demoblaze.com/");

		webDriver.findElement(By.linkText("Phones")).click();

		Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
		wait.until(d -> d.findElement(By.linkText("Samsung galaxy s6")));
		if (webDriver.findElement(By.linkText("Samsung galaxy s6")) != null) {
			System.out.println("Found Samsung galaxy s6");
		} else {
			System.out.println("Not found Samsung galaxy s6");
		}
		webDriver.navigate().back();
		;
		webDriver.navigate().refresh();
		webDriver.manage().window().fullscreen();
		System.out.println("Title : " + webDriver.getTitle());
		System.out.println("Length : " + webDriver.manage().window().getSize());
	}

}
