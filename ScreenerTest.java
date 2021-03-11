package app.screenertest.export;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import app.screenertest.datamanager.CSVDataManager;
import app.screenertest.datamanager.ExcelDataManager;
import app.screenertest.datamanager.FileStorageManager;


public class ScreenerTest {

    private static WebDriver driver;
    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
        
    }
    @Before
   public void init(){
        ChromeOptions options = new ChromeOptions();
         
    	 DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    	 options.addArguments("enable-automation");
         
         Map<String, Object> prefs = new LinkedHashMap<>();
         
         prefs.put("download.default_directory",System.getProperty("user.dir")+"\\src\\main\\resources\\output\\");
         options.setExperimentalOption("prefs", prefs);
         driver = new ChromeDriver(options);
         
         capabilities.setCapability(ChromeOptions.CAPABILITY, options);
         driver.manage().window().maximize();  
         driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
    	 driver.get("https://www.screener.in");
    }
    
  
  @Test
    public void ExportToExcel()
    {
	   login();
	   String basepath=System.getProperty("user.dir")+"\\src\\main\\resources";
	   String ipfolderpath=basepath+"\\input\\";
	   String opfolderpath=basepath+"\\output\\";
	   
	   String ipfilename="equity.csv";
	   
	   
	   List<String[]> securitylist= new CSVDataManager(ipfolderpath+ipfilename).getCsvData();
	   System.out.println("start...");
	   for(String[] securitydata: securitylist) 
	   {
		   int ISIN = Integer.parseInt(securitydata[0]);
		   
		   int dload=0;
		   try {
			   dload =new FileStorageManager().getCountOfFilesFromPath(opfolderpath,".xlsx");
		   }catch(Exception e) {
			   
		   }
		   try {
			   driver.get("https://www.screener.in/company/"+ISIN+"/consolidated/");
			   WebDriverWait wait = new WebDriverWait(driver, 5);
			   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='top']/div/div/button/span")));
			   wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='top']/div/div/button/span")));
			   driver.findElement(By.xpath("//div[@id='top']/div/div/button/span")).click();
			   while(new FileStorageManager().getCountOfFilesFromPath(opfolderpath,".xlsx")!=dload+1)
			   {
				   sleep(1000);
			   }
		   }catch( Exception Ex) {
			  System.out.println("Excel not found for ISIN : "+ISIN+"-"+securitydata[2]+"-"+securitydata[3]);
		  }
		  
	   }
	   
	   
	   
	   
	   	   
    }

  	@Test
    public void CreateSummary() {
  	   
 	   String basepath=System.getProperty("user.dir")+"\\src\\main\\resources";
 	   String opfolderpath=basepath+"\\output\\";	
 	   String resfolderpath=basepath+"\\result\\";
	   String resfilename = "resultfile.csv";
	   
 	   List<File> files = new FileStorageManager().getAllFilesListFromPath(opfolderpath, ".xlsx");
 	   List<String[]> companyInfo = new ArrayList<>();
 	   
 	   String header[] = {"NAME","MCAP","CMP","PE","PE-TTM","EarningScore","DebtScore","CashScore"
        		,"CompanyScore","G-IV","A-IV","P-IV","G-Valuation","A-Valuation","P-Valuation"
        		,"G-ROI","A-ROI","P-ROI","G-Decision","A-Decision","P-Decision"};
        
 	   companyInfo.add(header);
        int rec=1;
       System.out.println("Excel Summary reading started");
 	   for(File file : files) {
 		   try {
 			   FileInputStream inputStream = new FileInputStream(file);
 			companyInfo.add(new ExcelDataManager().readExcel(inputStream,"summary"));
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		 System.out.println(rec+"-"+file.toString()); 
 		 rec++;
 	   }
 	  System.out.println("Excel Summary reading completed");
 	   new CSVDataManager(resfolderpath+resfilename).putCsvData(companyInfo);

    }
    
  	@Test
  	public void scoredownload() {
  		
  		 String basepath=System.getProperty("user.dir")+"\\src\\main\\resources";
   	   String opfolderpath=basepath+"\\output\\";	
   	   String resfolderpath=basepath+"\\result\\";
  	   String resfilename = "Scorefile.csv";
  	   
   	   List<File> files = new FileStorageManager().getAllFilesListFromPath(opfolderpath, ".xlsx");
   	   List<String[]> companyInfo = new ArrayList<>();
   	   
   	   String header[] = 
   		   	{   "NAME","MCAP","CMP","PE"
   			   ,"Earning1","Earning2","Earning3","Earning4"
   			   ,"DebtScore1","DebtScore2","DebtScore3","DebtScore4","DebtScore5"
   			   ,"CashScore"
   			   ,"IV-Max","IV-Avg","IV-Min"
          	   ,"CAGR-Max","CAGR-Avg","CAGR-Min"
   			   ,"ReturnMultiple-Max","ReturnMultiple-Avg","ReturnMultiple-Min"};
          
   	   	companyInfo.add(header);
        int rec=1;
        System.out.println("Excel score download reading started");
   	   	for(File file : files) {
	   		try {
	   			   FileInputStream inputStream = new FileInputStream(file);
	   			companyInfo.add(new ExcelDataManager().downloadScore(inputStream,"summary"));
	   		} catch (IOException e) {
	   			// TODO Auto-generated catch block
	   			e.printStackTrace();
	   		}
	   		 
	   		System.out.println(rec+"-"+file.toString()); 
	   		rec++;
   		  
   	   	}
   	   	System.out.println("Excel Summary reading completed");
   	   	new CSVDataManager(resfolderpath+resfilename).putCsvData(companyInfo);

  	}
  	
  	@After
    public void tearDown(){
    	System.out.println("Program completed");
        driver.close();
    }
    
    public void sleep(int sec) {
    	try {
			   Thread.sleep(sec);
		   } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
    }
    
    public void login() {
    	
    	driver.findElement(By.xpath("//a[contains(@href, '/login/')]")).click(); 
    	while(!driver.getTitle().equalsIgnoreCase("Login - Screener")) 
    	 {
    		 sleep(3000);
    	 } 
    	 driver.findElement(By.id("id_username")).sendKeys("uitest@robot-mail.com");
         
         driver.findElement(By.id("id_password")).sendKeys("Pass@123");
         
         driver.findElement(By.xpath("//button[@type='submit']")).click();;     
    	
    }
    
    
}
