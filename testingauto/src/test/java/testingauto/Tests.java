package testingauto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import data.Data;
import pages.PageLogin;
import pages.PageLogon;
import pages.PageReservation;


public class Tests {

	private WebDriver driver;
	private Data data;
	private List<Map<String,String>> list;
	ArrayList<String> tabs;
	
	@BeforeMethod
	public void setUp() throws IOException, Exception, InvalidFormatException {

		DesiredCapabilities caps = new DesiredCapabilities();
		String exePath = "Chrome Driver\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
		driver = new ChromeDriver();
		
		data = new Data("./data.xlsx");
		list = data.getData();
		
		//driver.manage().window().maximize();
		//driver.manage().window().maximize();
		

		
		//driver.manage().window().setSize(new Dimension(200,400));
       
		driver.navigate().to("http://newtours.demoaut.com/");

		/*Helpers helper = new Helpers();
		helper.sleepSeconds(5);*/

	}
		
		
	/*
	@Test
	public void incorrectLogin() {

		PageLogin pageLogin = new PageLogin(driver);
		PageLogon pageLogon = new PageLogon(driver);
		pageLogin.login("user", "user");	
		pageLogon.assertLogonPage();

	}*/
	
	
	@Test
	public void multipleLogin() throws Exception {
		PageLogin pageLogin = new PageLogin(driver);
		
		pageLogin.loginXTimes("http://newtours.demoaut.com/");
		
		
		
		tabs = new ArrayList<String> (driver.getWindowHandles());
		
		for(int k=0;k<tabs.size();k++) {
			
			driver.switchTo().window(tabs.get(k));
			Boolean result = Boolean.parseBoolean(list.get(k).get("assert"));
			if(result) {
				
				PageReservation pageReservation = new PageReservation(driver);
				pageReservation.assertPage();
				
			}else {
				
				PageLogon pageLogon = new PageLogon(driver);
				pageLogon.assertLogonPage();
			}
			
			driver.switchTo().window(tabs.get(k)).close();
			
		}
		
		
	}

	/*@Test
	public void correctLogin() {
		
		PageLogin pageLogin = new PageLogin(driver);
		PageReservation pageReservation = new PageReservation(driver);
		pageLogin.login("mercury", "mercury");
		pageReservation.assertPage();

	}
	@Test
	public void flyRegistration() {
		System.out.println("reservation");
		PageLogin pageLogin = new PageLogin(driver);
		PageReservation pageReservation = new PageReservation(driver);
		pageLogin.login("mercury", "mercury");
		pageReservation.selectPassengers(2);
		pageReservation.selectFromPort(3);
		pageReservation.selecttoPort("London");
	}
	
	@Test
	public void pruebaCantidadDeCampos() {
		System.out.println("Verify fields");
		PageLogin pageLogin = new PageLogin(driver);
		
		pageLogin.verifyFields();
		
	}*/

	@AfterMethod
	public void tearDown(ITestResult result) throws FileNotFoundException, IOException {
		
		if(!result.isSuccess()) {
			File myScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				System.out.println("Creando captura");
				
				FileUtils.copyFile(myScreenshot, new File("Error " + System.currentTimeMillis()  + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		String tcName = result.getName();
		Boolean success = result.isSuccess();
		ArrayList<Object> list = new ArrayList<Object>();
		LocalDateTime date = LocalDateTime.now();
		String day = this.getDateOrTime(date,true);
		String hour = this.getDateOrTime(date,false);
		list.add(tcName);
		list.add(success);
		list.add(day);
		list.add(hour);
		data.insertRow(list);

	}
	
	
	public String getDateOrTime(LocalDateTime timeDate, Boolean isDate) {
		
		String date = "";
		String a="";
		String b="";
		String c="";
		
		String sep = "";
		
		if(isDate) {
			a=String.format("d", timeDate.getDayOfMonth());
			b= String.format("m", timeDate.getMonthValue());
			c= String.format("Y", timeDate.getYear());
			sep = "/";	
			
			
		}else {
			
			a=String.format("H", timeDate.getDayOfMonth());
			b= String.format("M", timeDate.getMinute());;
			c= String.format("S", timeDate.getSecond());;
			sep = ":";	
			
		}
		date += a + sep + b + sep + c;
		
		
		
		return date;
		
		
	}
	

}
