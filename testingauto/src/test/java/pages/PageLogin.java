package pages;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import assertpage.AssertPages;
import data.Data;
import helpers.Helpers;


public class PageLogin {

	private WebDriver driver;
	private By userField;
	private By pwdField;
	private By loginButton;
	private By signOn;
	private By fields;
	private String username = "userName";
	private String pass = "password";
	ArrayList<String> tabs;
	
	
	public PageLogin(WebDriver driver) {

		this.driver = driver;
		userField = By.name("userName");
		pwdField = By.name("password");
		loginButton = By.name("login");
		fields = By.tagName("input");
		signOn = By.partialLinkText("SIGN-ON");
	}
	
	
	public void loginXTimes(String url, List<Map<String,String>> list) throws Exception {
		Data data = new Data("./data.xlsx");
		
		
		tabs = new ArrayList<String> (driver.getWindowHandles());
		for(int i=0;i<list.size();i++) {
			
			JavascriptExecutor jsExe = (JavascriptExecutor) driver;
			String googleWin = "window.open('"+url+"')";
			jsExe.executeScript(googleWin);
			
			
			
		}
		
		driver.switchTo().window(tabs.get(0)).close();
		
		tabs = new ArrayList<String> (driver.getWindowHandles());
		for(int j=0;j<tabs.size();j++) {

			driver.switchTo().window(tabs.get(j));
			this.filter(j, list, true, false);

		}
		
		for(int j=0;j<tabs.size();j++) {
			
			driver.switchTo().window(tabs.get(j));
			driver.findElement(loginButton).click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
		}
		
		AssertPages assertPages = new AssertPages(driver);
		assertPages.assertMultipleTabs(tabs, list);
		
	
		
		
	}
	
	
	public void filter(Integer a, List<Map<String,String>> list, Boolean isSigOn, Boolean reservate) throws InterruptedException {
		
		Map<String,String> map = list.get(a);
		Boolean bool = Boolean.parseBoolean(map.get("assert"));
		if(isSigOn) {
			
			loginPage();
		
		}
		this.login(map.get("userName"), map.get("password"));
		driver.findElement(loginButton).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		if(!reservate) {
			
			AssertPages assertPages = new AssertPages(driver);
			assertPages.correctOrIncorrect(bool,map.get("expectedResult"));
			
		}
		
		
		
	}
	
	public void loginPage() {
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(signOn).click();
		
	}

	public void login(String user, String pass) throws InterruptedException {
		
		System.out.println("PageLogin/Login");
		System.out.println(user);
		System.out.println(pass);

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		
		
		driver.findElement(userField).sendKeys(user);
		driver.findElement(pwdField).sendKeys(pass);




	}



}
