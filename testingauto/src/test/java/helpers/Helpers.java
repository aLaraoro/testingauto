package helpers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public class Helpers {
	

	public Helpers() {
		
	}

	public void sleepSeconds(int seconds) {

		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	


}
