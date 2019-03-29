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

import data.Data;


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
			String usernameList = list.get(j).get(username);
			String passList = list.get(j).get(pass);
			driver.switchTo().window(tabs.get(j));
			
			this.login(usernameList, passList);

		}
		
	
		
		
	}

	public void login(String user, String pass) throws InterruptedException {
		Thread.sleep(5000);
		driver.findElement(signOn).click();
		driver.findElement(userField).sendKeys(user);
		driver.findElement(pwdField).sendKeys(pass);
		driver.findElement(loginButton).click();
		System.out.println("Login Button: "+driver.findElement(loginButton).isEnabled());	
		Thread.sleep(4000);

	}
	
	public void fields_login(String user, String pass) {
		
		List<WebElement> loginFields = driver.findElements(fields);
		loginFields.get(1).sendKeys(user);
		loginFields.get(1).sendKeys(pass);
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	}
	
	public void verifyFields() {
		
		List<WebElement> loginFields = driver.findElements(fields);
		System.out.println(loginFields.size());
		Assert.assertTrue(loginFields.size()==5, "El tamaño de la lista es diferente a 5");
		
	}



}
