package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class PageReservation {
	
	
	private WebDriver driver;
	private By titleText;
	private By passengersDrop;
	private By fromDrop;
	private By arrivals;
	
	public PageReservation(WebDriver driver) {
		
		this.driver = driver;
		titleText = By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[3]/td/font");
		passengersDrop = By.name("passCount");
		fromDrop = By.name("fromPort");
		arrivals = By.name("toPort");
	
	}
	
	public void assertPage() {
		
		Assert.assertTrue(driver.findElement(titleText).getText().contains("Flight Finder"));
		
	}
	
	public void selectPassengers(int count) {
		Select selectPassengers = new Select(driver.findElement(passengersDrop));
		selectPassengers.selectByVisibleText(Integer.toString(count));
		
	}
	
	public void selectFromPort(int index) {
		
		Select selectFromPort = new Select(driver.findElement(fromDrop));
		selectFromPort.selectByIndex(index);
		
	}
	
	public void selecttoPort(String city) {
		
		Select selectFromPort = new Select(driver.findElement(fromDrop));
		selectFromPort.selectByValue(city);
		
	}

}
