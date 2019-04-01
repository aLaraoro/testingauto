package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
	}
	
	
	public void register() {
		
		driver.findElement(register).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driver.findElement(firstName).sendKeys("a");
		driver.findElement(lastName).sendKeys("a");
		driver.findElement(phone).sendKeys("644223966");;
		driver.findElement(email).sendKeys("a@gmail.com");
		driver.findElement(address).sendKeys("Av. de la Torre Blanca, 57");;
		driver.findElement(city).sendKeys("Sant Cugat del Vallés");;
		driver.findElement(state).sendKeys("New York");;
		driver.findElement(postcode).sendKeys("08172");
		driver.findElement(userName).sendKeys("user");
		driver.findElement(pass).sendKeys("user");
		driver.findElement(confirmpass).sendKeys("user");
	
		
		
		
		
	}

}
