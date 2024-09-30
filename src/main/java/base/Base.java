package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.edge.EdgeDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Base {
	
	// Declare common variables for driver, reports, and properties
	protected static EdgeDriver driver;
	protected static ExtentSparkReporter htmlreport;
	protected static ExtentReports report;
	protected static ExtentTest test;
	public static Properties prop = new Properties();

	// Method to configure and create the Extent report
	public void report() throws IOException {
		htmlreport = new ExtentSparkReporter(new File("C:\\Users\\MohithaP\\Desktop\\Practo_ExtentReports.html"));
		htmlreport.config().setReportName("Practo");
		htmlreport.config().setDocumentTitle("Practo Test");
		htmlreport.config().setTheme(Theme.DARK); // Set report theme to dark
		report = new ExtentReports();
		report.setSystemInfo("Environment", "TestEnv");
		report.setSystemInfo("TesterName", "Mohitha P");
		report.attachReporter(htmlreport);

		// Load properties from the Config.proprties file
		FileInputStream input = new FileInputStream("src/main/java/base/Config.properties");
		prop.load(input);
	}

	// Method to set up the EdgeDriver and configure basic browser settings
	public void setUp() throws Exception {
		driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20)); // Set implicit wait timeout
		System.out.println("url setup");
	}

	// Method to open the URL which is provided in the properties file
	public void openUrl() throws Exception {
		String url1 = prop.getProperty("url");
		driver.get(url1); // Navigate to the URL
		System.out.println("The title of the browser is:  " + driver.getTitle());
	}

	// Method to close the browser and end the WebDriver session
	public void close() throws Exception {
		System.out.println("Browser closed");  //for understanding purpose
		driver.quit(); // Close all browser windows and terminate WebDriver session
	}
}
