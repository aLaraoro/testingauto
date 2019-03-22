package testingauto;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helpers.Helpers;
import pages.PageLogin;
import pages.PageLogon;
import pages.PageReservation;

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
	public void incorrectLogin() {
		PageLogin pageLogin = new PageLogin(driver);
		PageLogon pageLogon = new PageLogon(driver);
		pageLogin.login("user", "user");	
		pageLogon.assertLogonPage();

	}

	@Test
	public void correctLogin() {
		PageLogin pageLogin = new PageLogin(driver);
		PageReservation pageReservation = new PageReservation(driver);
		pageLogin.login("mercury", "mercur");
		pageReservation.assertPage();

	}
	@Test
	public void flyRegistration() {
		PageLogin pageLogin = new PageLogin(driver);
		PageReservation pageReservation = new PageReservation(driver);
		pageLogin.login("mercury", "mercury");
		pageReservation.assertPage();
		pageReservation.selectPassengers(2);
		pageReservation.selectFromPort(3);
		pageReservation.selecttoPort("London");
	}
	
	@Test
	public void pruebaCantidadDeCampos() {
		
		PageLogin pageLogin = new PageLogin(driver);
		
		pageLogin.verifyFields();
		
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		
		if(!result.isSuccess()) {
			File myScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				System.out.println("Creando captura");
				
				FileUtils.copyFile(myScreenshot, new File("LOGIN " + System.currentTimeMillis()  + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		driver.close();

	}

}
