package assertpage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import pages.PageReservation;

public class AssertPages {
	private WebDriver driver;
	private By reservationTitle;
	private By logonOn;
	private By signOff;
	private By depart;
	private String flyPath;
	private By toPort;
	private By path;


	public AssertPages(WebDriver driver) {

		this.driver = driver;
		reservationTitle = By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[3]/td/font");
		logonOn = By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[3]/td/p/font/b");
		signOff = By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table/tbody/tr/td[1]/a");
		flyPath = ".//b/font[text()='PATH']//following::font[contains(text(),'String')]//preceding::td[1]";
	}
	
	
	public void assertSelectFlight(List<String> confirmList) {
		String departure = confirmList.get(0);
		String to = confirmList.get(1);
		System.out.println(flyPath);
		System.out.println("Departure: "+flyPath.replace("PATH", "DEPART").replace("String", departure));
		depart = By.xpath(flyPath.replace("PATH", "DEPART").replace("String", departure));
		toPort = By.xpath(flyPath.replace("PATH", "RETURN").replace("String", to));
		
		List<WebElement> elements = driver.findElements(depart);
		WebElement elem = elements.get(0);
		String from = elem.getText();
		System.out.println("Departure: "+elements.size());
		
	}


	public void assertMultipleTabs(ArrayList<String> tabs,List<Map<String,String>> list) {

		for(int i=0;i<tabs.size();i++) {

			driver.switchTo().window(tabs.get(i));
			Boolean result = Boolean.parseBoolean(list.get(i).get("assert"));

			this.correctOrIncorrect(result,list.get(i).get("expectedResult"));

			driver.switchTo().window(tabs.get(i)).close();


		}


	}
	
	public void assertFlights(Boolean bool, String condition) {
		
		if(bool) {
			
			Assert.assertTrue(driver.findElement(reservationTitle).getText().contains(condition),"Expected Result: " + condition + ". Page: " + driver.getTitle());
			
		}else {
			
			Assert.assertTrue(driver.getCurrentUrl().contains("index.php"), "Expected Result: " + condition + ". Page: " + driver.getTitle());
		
		}
		
		
	}
	
	
	public void assertRegister(Boolean bool, String condition) {
		
		if(bool) {
			
			Assert.assertTrue(driver.findElement(signOff).getText().contains("SIGN-OFF"), "User is not registered");
			
		}else {
			
			Assert.assertFalse(driver.findElement(signOff).getText().contains("SIGN-OFF"), "User with: " + condition + " is properly registered");
			
		}
		
		
	}


	public void correctOrIncorrect(Boolean bool, String expectedRes) {
		System.out.println(bool);
		if(bool) {
			
			path = reservationTitle;

		}else {
			
			path = logonOn;

		}
		Assert.assertTrue(driver.findElement(path).getText().contains(expectedRes),"Expected Result: " + expectedRes + ". Page: " + driver.getTitle());
		


	}

}
