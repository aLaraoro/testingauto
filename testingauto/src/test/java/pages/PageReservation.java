package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PageReservation {


	private WebDriver driver;
	private By passengersDrop;
	private By fromDrop;
	private By arrivals;
	private By flights;

	public PageReservation(WebDriver driver) {

		this.driver = driver;
		passengersDrop = By.name("passCount");
		fromDrop = By.name("fromPort");
		arrivals = By.name("toPort");
		flights = By.partialLinkText("Flights");
	}
	
	public void reservationLogin(Boolean bool) {
		String title = driver.getTitle();
		driver.findElement(flights).click();
		if(!bool) {
			
			Assert.assertTrue(driver.getTitle().equalsIgnoreCase(title));
			
		}
		
		
	}


	public void selectPassengers(int count) {
		WebDriverWait wait = new WebDriverWait(driver,10);
		WebElement psngrQuant = wait.until(ExpectedConditions.presenceOfElementLocated(passengersDrop));
		Select selectPassengers = new Select(psngrQuant);
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
