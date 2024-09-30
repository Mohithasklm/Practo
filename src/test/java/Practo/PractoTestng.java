package Practo;

import java.io.IOException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import Pages.DoctorsPage;
import Pages.HomePage;
import base.Base;

public class PractoTestng extends Base {
    HomePage h = new HomePage();
    DoctorsPage d = new DoctorsPage();

    // Initialize ExtentReports before running the tests
    @BeforeSuite
    public void setupReport() throws IOException {
        h.report();
    }

    // Test to verify the list of all doctors based on selected specialty
    @Test(priority = 1)
    public void verifyListOfAllDoctors() throws Exception {
        h.setUp();    // Setup the browser and WebDriver
        h.openUrl();     // Open the practo URL 
        h.selectCity(); // Select the city based on the config.properties file
        
        test = report.createTest("Validate Doctors specialists");
        // Validate if the selected specialty matches the doctors specialty or not 
        int res[] = h.selectSpecality();
        if (res[1] == 0) {
            System.err.println("pass");
            test.log(Status.PASS, "Doctor specialist type is same as selected specialist");
        } else {
            System.err.println("Fail");
            test.log(Status.FAIL, "Doctor specialist type is NOT same as selected specialist");
        }

        h.close(); // Close the browser
    }

    // Test to verify the doctor's name, appointment date, and time
    @Test(priority = 2)
    public void verifyDoctorNameDateTime() throws Exception {
        h.setUp();
        h.openUrl();
        h.selectCity();
        h.selectSpecality();
        test = report.createTest("Validate Doctor appointed Time");
        int res[]=d.doctorName();// Check if selected doctor name and time match the expected
        if(res[0]==1)
            test.log(Status.PASS, "Both user selected time and appointed time are the same");
        else
            test.log(Status.FAIL, "User selected time and appointed time are NOT the same");
        test = report.createTest("Validate Doctor Name");
        if(res[1]==1)
            test.log(Status.PASS, "Both user selected doctor name and appointed doctor name are the same");
        else
            test.log(Status.FAIL, "User selected doctor name and appointed doctor name are NOT the same");

        int dateRes=d.doctorDate();
        test = report.createTest("Validate Doctor appointed Date");
        if(dateRes==1)
        	test.log(Status.PASS, "Both user selected date and appointed date are the same");
        else
            test.log(Status.FAIL, "User selected date and appointed date are NOT the same");

        h.close();
    }

    // Test to verify the consultation fee is filtered correctly
    @Test(priority = 3)
    public void verifyFilteredConsulatationFee() throws Exception {
        h.setUp();
        h.openUrl();
        h.selectCity();
        h.selectSpecality();
        // Validate if the consultation fee is equal or above the filtered value
        int[] res = d.consultationFee();
        test = report.createTest("Validate filtered Consultation Fee");
        if (res[1] == 0) {
            System.err.println("pass: All the consultation fees of all doctors are as per the value filtered.");
            test.log(Status.PASS, "All the consultation fees of all doctors are as per the value filtered.");
        } else {
            System.err.println("fail:All the consultation fees of all doctors are NOT as per the value filtered. ");
            test.log(Status.FAIL, "All the consultation fees of all doctors are NOT as per the value filtered.");
        }
        h.close();
    }

    // Save ExtentReports after all tests are completed
    @AfterSuite
    public void flushReports() {
        report.flush();
    }
}
