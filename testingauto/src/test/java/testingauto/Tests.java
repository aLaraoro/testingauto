package testingauto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import assertpage.AssertPages;
import data.Data;
import pages.PageLogin;


public class Tests {

	private WebDriver driver;
	private Data data;
	private List<Map<String,String>> list;
	private List<String> loginList;
	private List<Map<String,String>> autoLogin;
	private Integer row;
	ArrayList<String> tabs;
	
	@BeforeClass
	public void init() throws IOException, Exception, InvalidFormatException {
		
		//Data
		data = new Data("./data.xlsx");
		list = data.getData();
		loginList = data.getLoginData(list, true);
		autoLogin = data.autoList(loginList, 5);
		
	}
	
	@BeforeMethod
	public void setUp() {

		DesiredCapabilities caps = new DesiredCapabilities();				
		
		//Inicia driver
		String exePath = "Chrome Driver\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
		driver = new ChromeDriver();
       
		driver.navigate().to("http://newtours.demoaut.com/");


	}
		
		
	/*
	@Test
	public void incorrectLogin() {

		PageLogin pageLogin = new PageLogin(driver);
		PageLogon pageLogon = new PageLogon(driver);
		pageLogin.login("user", "user");	
		pageLogon.assertLogonPage();

	}*/
	
	
	/*
	
	@Test
	public void multipleLogin() throws Exception {
		System.out.println("Multi Login");
		System.out.println("From Data Excell");
		
		PageLogin pageLogin = new PageLogin(driver);
		AssertPages assertPages = new AssertPages(driver);
		pageLogin.loginXTimes("http://newtours.demoaut.com/", list);
		tabs = new ArrayList<String> (driver.getWindowHandles());
		
		assertPages.assertMultipleTabs(tabs, list);
		
	}*/
	
	@Test
	public void correctLoginNTimes() throws Exception{
		System.out.println("Correct Login");
		System.out.println("5 times");
		PageLogin pageLogin = new PageLogin(driver);
		pageLogin.loginXTimes("http://newtours.demoaut.com/", autoLogin);
		driver.close();
		
		
	}
	
	@Test
	public void loginEmptyPass() throws InterruptedException {
		System.out.println("Login");
		System.out.println("Empty password");
		PageLogin pageLogin = new PageLogin(driver);
		row=3;
		pageLogin.filter(row, list);
		driver.close();
		
	}
	
	
	
	@Test
	public void loginEmptyUser() throws InterruptedException {
		System.out.println("Login");
		System.out.println("Empty user");
		PageLogin pageLogin = new PageLogin(driver);
		row = 4;
		pageLogin.filter(row, list);
		driver.close();
		
	}
	
	@Test
	public void loginEmptyBoth() throws InterruptedException {
		System.out.println("Login");
		System.out.println("Both empty");
		PageLogin pageLogin = new PageLogin(driver);
		row = 5;
		pageLogin.filter(row, list);
		driver.close();
		
	}

	@Test
	public void correctLogin() throws InterruptedException {

		System.out.println("Correct Login");
		PageLogin pageLogin = new PageLogin(driver);
		row = 1;
		pageLogin.filter(row, list);
		driver.close();
		

	}/*
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
				System.out.println(result.getName());
				FileUtils.copyFile(myScreenshot, new File("Error " + System.currentTimeMillis()  + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("Tests/tearDown/TC Num:"+row);
		this.tcResult(result);
		driver.quit();

	}
	
	
	public void tcResult(ITestResult result) throws FileNotFoundException, IOException {
		
		String[] message = {"Message","null"};
		
		if (result.getThrowable() != null) {
			
			String a = result.getThrowable().getMessage();
			
			message[1] = a;
		}
		
		Map<Integer,String[]> map = new HashMap<Integer,String[]>();
		
		String[] tcName = {"TC Name",result.getName()};
		
		String[] expectedResult = {"Expected Result",list.get(row).get("expectedResult")};
		
		String[] title = {"Page title",""};
		
		Boolean resultSuc = result.isSuccess();
		if(!resultSuc) {
			
			title[1] = driver.getTitle();
			
		}
		
		String[] success = {"Success",StringUtils.capitalize(resultSuc.toString())};
		ArrayList<String> list = new ArrayList<String>();
		LocalDateTime date = LocalDateTime.now();
		String[] day = {"Day",this.getDateOrTime(date,true)};
		String[] hour = {"Hour",this.getDateOrTime(date,false)};

		
		map.put(0, tcName);
		map.put(1, day);
		map.put(2, hour);
		map.put(3, expectedResult);
		map.put(4, success);
		map.put(5, message);
		map.put(6, title);
		data.insertRowMap(map);
		
		
	}
	
	
	public String getDateOrTime(LocalDateTime timeDate, Boolean isDate) {
		
		String date = "";

		String pattern;
		
		if(isDate) {
			
			pattern = "dd-MM-yyyy";
			DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern(pattern);
			
			date = timeDate.format(simpleDateFormat);
			
			
		}else {
			
			pattern = "HH:mm:ss";
			DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern(pattern);
			
			date = timeDate.format(simpleDateFormat);
			
		}
		
		
		
		return date;
		
		
	}
	

}
