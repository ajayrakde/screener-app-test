package app.screenertest.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import app.screenertest.datamanager.FileStorageManager;

public class CompanyPage {

	WebDriver driver;
	
	public CompanyPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver downloadExcel(String opfolderpath, String ISIN) 
	{
		int dload=0;
		try {
			dload =new FileStorageManager().getCountOfFilesFromPath(opfolderpath,".xlsx");   
			driver.get("https://www.screener.in/company/"+ISIN+"/");
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='top']/div/div/button/span")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='top']/div/div/button/span")));
			driver.findElement(By.xpath("//div[@id='top']/div/div/button/span")).click();
			while(new FileStorageManager().getCountOfFilesFromPath(opfolderpath,".xlsx")!=dload+1)
			{
				Thread.sleep(1000);
			}
                } catch (InterruptedException e) {
                        e.printStackTrace();
                } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                }
		return this.driver;
	}
	
}
