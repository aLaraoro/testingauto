package assertpage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import pages.PageReservation;

public class AssertPages {
	private WebDriver driver;
	private By reservationTitle;
	private By logonOn;
	private By signOff;
	
	
	public AssertPages(WebDriver driver) {
		
		this.driver = driver;
		reservationTitle = By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[3]/td/font");
		logonOn = By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[3]/td/p/font/b");
		signOff = By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table/tbody/tr/td[1]/a");
		
	}
	
	
	public void assertMultipleTabs(ArrayList<String> tabs,List<Map<String,String>> list) {
		
		for(int i=0;i<tabs.size();i++) {
			
			driver.switchTo().window(tabs.get(i));
			Boolean result = Boolean.parseBoolean(list.get(i).get("assert"));
			
			this.correctOrIncorrect(result);
			
			driver.switchTo().window(tabs.get(i)).close();
			
			
		}
		
		
	}
	
	
	public void correctOrIncorrect(Boolean bool) {
		System.out.println(bool);
		if(bool) {
			
			Assert.assertTrue(driver.findElement(reservationTitle).getText().contains("Flight Finder"));
			
		}else {
			
			Assert.assertTrue(driver.findElement(logonOn).getText().contains("Welcome back to"));
			
		}
		
		
	}

}
