package Practo;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Pages.DoctorsPage;
import Pages.HomePage;
import base.Base;

public class PractoTestng extends Base{
	HomePage h=new HomePage();
	DoctorsPage d=new DoctorsPage();
	
	@BeforeSuite
	public void setupReport() {
		h.report(); 
	}
	
	@Test(priority=1)
    public void verifyListOfAllDoctors() throws Exception {
		h.setUp(); 
		h.openUrl();
    	h.selectCity();
    	test=report.createTest("Validate DoctorName,Date AND Time");
    	int res[]=h.selectSpecality();
    	if(res[1]==0) {
    		System.err.println("pass");
    		test.log(Status.PASS, "DoctorName,Date AND Time are equal");
    	}
    	else {
    		System.err.println("Fail");
    		test.log(Status.FAIL, "DoctorName,Date AND Time are NOT equal");
    	}
    	
    	h.close();
    	
    }
	
	@Test(priority=2)
    public void verifyDoctor_name_date_time() throws Exception {
		h.setUp(); 
		h.openUrl();
    	h.selectCity();
    	h.selectSpecality();
    	d.doctorName_Time();
    	
    	h.close();
    	
    }
	
   @Test(priority=3)
   public void filter()throws Exception {
	h.setUp(); 
	h.openUrl();
	h.selectCity();
	h.selectSpecality();
	test=report.createTest("Validate filtered sConsultation Fee");
	int []res=d.consultationFee();
	if(res[1]==0) {
		System.out.println("pass:  all prices are equal");
	    test.log(Status.PASS, "all the consultation fee of all doctors is as per the value filtered.");}
	else {
		System.out.println("fail:all prices are not equal");
		test.log(Status.FAIL, "all the consultation fee of all doctors is NOT as per the value filtered.");}
	h.close();
    }

	@AfterSuite
	public void flushReports() {
		report.flush(); 
	}
	
	
	
}
