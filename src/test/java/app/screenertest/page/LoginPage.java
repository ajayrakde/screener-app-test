package app.screenertest.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;

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
                        e.printStackTrace();
                }
    	 }
    		
    	 try {
			driver.findElement(By.id("id_username")).sendKeys("uitest@robot-mail.com");
			 
			 driver.findElement(By.id("id_password")).sendKeys("Pass@123");
			 
                        driver.findElement(By.xpath("//button[@type='submit']")).click();
                } catch (NoSuchElementException e) {
                        e.printStackTrace();
                }
  		 return driver;        	 
    }
}
