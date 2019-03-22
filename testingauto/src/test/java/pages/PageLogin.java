package pages;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import helpers.Helpers;

public class PageLogin {

	private WebDriver driver;
	private By userField;
	private By pwdField;
	private By loginButton;
	private By fields;
	public PageLogin(WebDriver driver) {

		this.driver = driver;
		userField = By.name("userName");
		pwdField = By.name("password");
		loginButton = By.name("login");
		fields = By.tagName("input");
	}

	public void login(String user, String pass) {


		driver.findElement(userField).sendKeys(user);
		driver.findElement(pwdField).sendKeys(pass);
		driver.findElement(loginButton).click();
		
		/*File myScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			System.out.println("Creando captura");
			
			FileUtils.copyFile(myScreenshot, new File("LOGIN " + System.currentTimeMillis()  + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

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
