package pages;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import assertpage.AssertPages;

public class Register {

	private WebDriver driver;
	private By register;
	private By firstName;
	private By lastName;
	private By phone;
	private By email;
	private By address;
	private By city;
	private By state;
	private By postcode;
	private By userName;
	private By pass;
	private By confirmpass;
	private By submitBttn;
	public Register(WebDriver driver) {

		this.driver = driver;
		register = By.partialLinkText("REGISTER");

		firstName = By.name("firstName");
		lastName = By.name("lastName");
		phone = By.name("phone");
		email = By.id("userName");
		address = By.name("address1");
		city = By.name("city");
		state = By.name("state");
		postcode = By.name("postalCode");
		userName = By.name("email");
		pass = By.name("password");
		confirmpass = By.name("confirmPassword");
		submitBttn = By.name("register");

	}
	
	public Map<String,String> optional(String firstName, String lastName, String phone, String email, String address, String city, String state, String postcode) {
		
		Map<String, String> map = new HashMap<String,String>() {{
	        put("firstName", firstName);
	        put("lastName", lastName);
	        put("phone", phone);
	        put("email", email);
	        put("address", address);
	        put("city", city);
	        put("state", state);
	        put("postcode", postcode);
	    }};
		
		return map;
		
		
	}
	
	public Map<String,String> required(String user, String password, String confPass) {
		
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("userName", user);
		map.put("password", password);
		map.put("confirmpass", confPass);
		
		return map;
		
		
	}


	public void register(Map<String,String> map, Map<String,String> required) throws InterruptedException {

		driver.findElement(register).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		driver.findElement(firstName).sendKeys(map.get("firstName"));
		driver.findElement(lastName).sendKeys(map.get("lastName"));
		driver.findElement(phone).sendKeys(map.get("phone"));
		driver.findElement(email).sendKeys(map.get("email"));
		driver.findElement(address).sendKeys(map.get("address"));
		driver.findElement(city).sendKeys(map.get("city"));
		driver.findElement(state).sendKeys(map.get("state"));
		driver.findElement(postcode).sendKeys(map.get("postcode"));
		
		//Requeridas
		
		driver.findElement(userName).sendKeys(required.get("userName"));
		driver.findElement(pass).sendKeys(required.get("password"));
		driver.findElement(confirmpass).sendKeys(required.get("confirmpass"));
		
		
		driver.findElement(submitBttn).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		/*if(registerOn) {
			
				driver.findElement(SignIn).click();
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				PageLogin login = new PageLogin(driver);
				login.login(map.get("userName"), map.get("password"));
				assertPages.correctOrIncorrect(true,"Flight Finder");
				
		}*/

	}

}
