package base;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.edge.EdgeDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Base {
	 protected static EdgeDriver driver;
	 protected static ExtentSparkReporter htmlreport;
	 protected static ExtentReports report;
	 protected static ExtentTest test;
	 
	public void report() {
		    htmlreport=new ExtentSparkReporter(new File("C:\\Users\\MohithaP\\Desktop\\Practo_ExtentReports.html"));
			htmlreport.config().setReportName("Practo");
			htmlreport.config().setDocumentTitle("Practo Test");
			htmlreport.config().setTheme(Theme.DARK);
		    report=new ExtentReports();
			report.setSystemInfo("Environment", "TestEnv");
			report.setSystemInfo("TesterName", "Mohitha P");
			report.attachReporter(htmlreport);
	 }
	
	 public void setUp() throws Exception {	   
			
		    driver=new EdgeDriver();
		    driver.manage().window().maximize();
		    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		    System.out.println("url setup"); 
	 }
	 
	 public void openUrl() throws Exception {
		    Properties prop=new Properties();
			FileInputStream input=new FileInputStream("src/main/java/base/Config.properties");
			prop.load(input);
			String url1=prop.getProperty("url");
			driver.get(url1);
            System.out.println("The title of the browser is:  "+ driver.getTitle());
	 }
	 

	 public void close() throws Exception {
		 System.out.println("Browser closed");
	     driver.quit();     
	 } 

}
