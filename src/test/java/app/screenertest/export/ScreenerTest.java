package app.screenertest.export;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import app.screenertest.datamanager.CSVDataManager;
import app.screenertest.datamanager.ExcelDataManager;
import app.screenertest.datamanager.FileStorageManager;
import app.screenertest.page.CompanyPage;
import app.screenertest.page.LoginPage;


public class ScreenerTest {

    private static WebDriver driver;
    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
        
    }
    @Before
   public void init(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("enable-automation");
         
         Map<String, Object> prefs = new LinkedHashMap<>();
         
         prefs.put("download.default_directory",System.getProperty("user.dir")+"\\src\\main\\resources\\output\\");
         options.setExperimentalOption("prefs", prefs);
         driver = new ChromeDriver(options);
         driver.manage().window().maximize();  
         driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
    	 driver.get("https://www.screener.in");
    }
    
  
  @Test
    public void ExportToExcel()
    {
	   
	   LoginPage loginPage = new LoginPage(driver);
	   driver = loginPage.login();
	   
	   String basepath=System.getProperty("user.dir")+"\\src\\main\\resources";
	   String ipfolderpath=basepath+"\\input\\";
	   String opfolderpath=basepath+"\\output\\";
	   
	   String ipfilename="Equity.csv";
	   
	   
	   List<String[]> securitylist= new CSVDataManager(ipfolderpath+ipfilename).getCsvData();

	   System.out.println("start...");
	   for(String[] securitydata: securitylist) 
	   {
	   
		   CompanyPage companyPage = new CompanyPage(driver);
		   int i=1;
		   do
		   {
			   driver=companyPage.downloadExcel(opfolderpath, securitydata[i]);
			   i++;
                   }while(driver==null && i<=2);
                   if(driver==null)
                   {
                           System.out.println("FAILURE : Excel not found for ISIN : "
                                           + securitydata[0] + "-" + securitydata[2] + "(" + securitydata[3] + ")");
                   }
                   else
                   {
                           System.out.println("SUCCESS : Excel downloaded for ISIN : "
                                           + securitydata[0] + "-" + securitydata[2] + "(" + securitydata[3] + ")");
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
                        e.printStackTrace();
                }
    }
    
    
    
    
}
