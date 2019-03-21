package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import helpers.Helpers;

public class PageLogin {
	
	private WebDriver driver;
	private By userField;
	private By pwdField;
	private By loginButton;
	
	public PageLogin(WebDriver driver) {
		
		this.driver = driver;
		userField = By.name("userName");
		pwdField = By.name("password");
		loginButton = By.name("login");
		
	}
	
	public void login(String user, String pass) {
		
		
		driver.findElement(userField).sendKeys(user);
		driver.findElement(pwdField).sendKeys(pass);
		driver.findElement(loginButton).click();
		Helpers helper = new Helpers();
		helper.sleepSeconds(4);
		
	}
	
	public void currentPage(String name) {
		
		switch(name) {
		
		case "mercurysignon.php":
			PageLogon pageLogon = new PageLogon(driver);
			pageLogon.assertLogonPage();
			break;
		case "":
			PageReservation pageReservation = new PageReservation(driver);
			pageReservation.assertPage();
			break;
		default:
			break;
		
		
		}
		
		
		
	}
	

}
