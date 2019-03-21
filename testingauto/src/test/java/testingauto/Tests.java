package testingauto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helpers.*;
import pages.*;

public class Tests {
	
	private WebDriver driver;
	
	@BeforeMethod
	public void setUp() {
		
		DesiredCapabilities caps = new DesiredCapabilities();
		 String exePath = "Chrome Driver\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("http://newtours.demoaut.com/");
		Helpers helper = new Helpers();
		helper.sleepSeconds(5);

		
		
	}
	@Test
	public void pruebaUno() {
		PageLogin pageLogin = new PageLogin(driver);
		pageLogin.login("user", "user");		
		pageLogin.currentPage("mercurysignon.php");
		
	}
	
	@Test
	public void pruebaDos() {
		PageLogin pageLogin = new PageLogin(driver);
		pageLogin.login("mercury", "mercury");
		pageLogin.currentPage("");
		
	}
	@AfterMethod
	public void tearDown() {
		
		driver.close();
		
	}

}
