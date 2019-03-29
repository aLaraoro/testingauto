package assertpage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import pages.PageLogon;
import pages.PageReservation;

public class AssertPages {
	private WebDriver driver;
	private PageReservation pageReservation;
	private PageLogon pageLogon;
	
	public AssertPages(WebDriver driver) {
		
		this.driver = driver;
		
		
	}
	
	
	public void assertMultipleTabs(ArrayList<String> tabs,List<Map<String,String>> list) {
		
		for(int i=0;i<tabs.size();i++) {
			
			driver.switchTo().window(tabs.get(i));
			Boolean result = Boolean.parseBoolean(list.get(i).get("assert"));
			if(result) {
				
				PageReservation pageReservation = new PageReservation(driver);
				pageReservation.assertPage();
				
			}else {
				
				PageLogon pageLogon = new PageLogon(driver);
				pageLogon.assertLogonPage();
			}
			
			driver.switchTo().window(tabs.get(i)).close();
			
			
		}
		
		
	}
	
	
	public void correctOrIncorrect(Boolean bool) {
		System.out.println(bool);
		if(bool) {
			
			pageReservation = new PageReservation(driver);
			pageReservation.assertPage();
			
		}else {
			
			pageLogon = new PageLogon(driver);
			pageLogon.assertLogonPage();
			
		}
		
		
	}

}
