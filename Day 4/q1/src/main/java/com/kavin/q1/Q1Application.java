package com.kavin.q1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Q1Application {

	public static void main(String[] args) {
		WebDriver chromeDriver = new ChromeDriver();
		WebDriver edgeDriver = new EdgeDriver();
		WebDriver firefoxDriver = new FirefoxDriver();
		chromeDriver.get("https://www.google.com");
		edgeDriver.get("https://www.google.com");
		firefoxDriver.get("https://www.google.com");
		SpringApplication.run(Q1Application.class, args);
	}

}
