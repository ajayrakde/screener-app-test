package app.screenertest.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;

public class LoginPage {
	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver login() {
    	
    	driver.findElement(By.xpath("//a[contains(@href, '/login/')]")).click(); 
    	while(!driver.getTitle().equalsIgnoreCase("Login - Screener")) 
    	 {
    		 try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	 }
    		
    	 try {
			driver.findElement(By.id("id_username")).sendKeys("uitest@robot-mail.com");
			 
			 driver.findElement(By.id("id_password")).sendKeys("Pass@123");
			 
			 driver.findElement(By.xpath("//button[@type='submit']")).click();;
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		 return driver;        	 
    }
}
