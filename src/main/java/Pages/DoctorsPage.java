package Pages;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.Base;

public class DoctorsPage extends Base {
	    
    // Locators for doctor name, button, available slots, timings, and consultation fee filtering
    By dName = By.xpath("//*[@id='container']/div/div[4]/div/div[1]/div/div[3]/div[1]/div/div[1]/div[2]/a/div/h2");
    By btn = By.xpath("//*[@id='container']/div/div[4]/div/div[1]/div/div[3]/div[1]/div/div[2]/div/div/div[2]/div[1]/button");
    By slots_locator = By.xpath("//div[@data-qa-id='date_selector']");
    By notAvailable_day = By.xpath("//div[@data-qa-id='date_selector']");
    By timings_list = By.xpath("//div[@data-qa-id=\"slot_time\"]");
    By afterName_locator = By.xpath("//*[@id='container']/div[2]/div/div[1]/div/div[1]/div[3]/div/div[2]/div[1]");
    
    // Locators for date comparison
    By after_date_locator = By.xpath("(//div[@class=\"pure-u-1-2\"])[2]/span[2]");
    By dates_list_locator = By.xpath("//div[@data-qa-id='date_selector']/div[1]");
    
    // Locators for filtered consultation fees
    By filt_btn = By.xpath("//i[@data-qa-id=\"all_filters_icon\"]");
    By radio_btn = By.xpath("(//span[.=\"Above ₹500\"])[2]");
    By filter_doctors = By.xpath("//div[@class=\"uv2-spacer--xs-top\"]/span[1]");
    
    public static String available_slot_date; //used for validate date and time
    
    // Method to validate doctor's name 
    public int[] doctorName() throws Exception {
    	
        // Get doctor's name before selecting time
        WebElement doctorName = driver.findElement(dName);
        String beforeName = doctorName.getText();
        WebElement dctr_button = driver.findElement(btn);
        dctr_button.click();  // Click on the button to book an appointment
        // Validate the doctor's name
        List<WebElement> date_list = driver.findElements(dates_list_locator);  // Today, tomorrow, other dates
        List<WebElement> slots = driver.findElements(slots_locator);  // Slot container for each date
    	int i = 0;
        for (WebElement slot : slots) {
            String text = slot.getText();
            if (!text.contains("No")) {
                slot.click();  // Select an available slot
                available_slot_date = date_list.get(i).getText();  // Get the selected date
                Thread.sleep(1000);
                System.out.println("slot clicked");
                break;
            }
            i++;
        }  
        int timeReturns=doctorTime();
        String afterName = driver.findElement(afterName_locator).getText();
        System.out.println("Before Doctor name: " + beforeName);
        System.out.println("After Doctor name: " + afterName);
        if (beforeName.equals(afterName)) {
            System.err.println("Doctor name is the same as before");
            int[] res= {timeReturns,1};
            return res;
        }
        else {
            System.err.println("Doctor name is NOT the same as before");
            int[] res= {timeReturns,0};
            return res;
        } 
    }
    
    // Method to validate doctor's appointed time
    public int doctorTime() throws Exception {
    	// Validate the selected time slot
        List<WebElement> timings = driver.findElements(timings_list);
        Thread.sleep(1000);
        String before_time = timings.get(0).getText();  // Time before clicking on it
        timings.get(0).click();	 
        String after_time = driver.findElement(By.xpath("//*[@id='container']/div[2]/div/div[1]/div/div[1]/div[2]/div[1]/div[2]/span[2]")).getText();
        
        // Parse time strings to compare them
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
        LocalTime time1 = LocalTime.parse(before_time, timeFormatter);
        LocalTime time2 = LocalTime.parse(after_time, timeFormatter);
        
        System.out.println("Before time: " + time1);
        System.out.println("After time: " + time2);
        
        // Validate the selected time against the displayed time after selection
        if (time1 != null && time1.equals(time2)) {
            System.err.println("Timings are the same as before");
            return 1;
        } 
        else {
            System.err.println("Timings are NOT the same as before");
            return 0;
        }
         
    }
    
    // Method to validate doctor's appointed Date
    public int doctorDate() throws Exception {
         
         // Validate the appointment date
         Thread.sleep(2000);
         LocalDate today = LocalDate.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
         String todayDate = today.format(formatter); //today's date
         String before_date = "";
           
         LocalDate tomorrow = today.plusDays(1);
         String tomorrowDate = tomorrow.format(formatter); //tomorrow's date
      // Check if the selected slot is today, tomorrow, or another date
         if (available_slot_date.equals("Today")) {
             before_date = todayDate;
         } 
         else if (available_slot_date.equals("Tomorrow")) {
             before_date = tomorrowDate;
         }
         else {
             before_date = available_slot_date.formatted(formatter);
         }
         String after_date = driver.findElement(after_date_locator).getText(); 
         
         // Validate the selected date against the displayed date
         System.out.println("Before Date: " + before_date);
         System.out.println("After Date: " + after_date);
         if (before_date.equals(after_date)) {
             System.err.println("Selected date is the same as appointed date");
             return 1;
         } 
         else {
             System.err.println("Selected date is NOT the same as appointed date");
             return 0;
         }	
    } 
    
   
    // Method to validate consultation fees
    public int[] consultationFee() {	   
        driver.findElement(filt_btn).click();  // Open the filter menu
        driver.findElement(radio_btn).click();  // Select "Above ₹500" option
        List<WebElement> filter_list = driver.findElements(filter_doctors);
        int pass = 0, fail = 0;
        
        for (WebElement w : filter_list) {
            String feeText = w.getText().replaceAll("[^0-9]", "");  // Keep only numeric values
            if (!feeText.isEmpty()) {
                int fee = Integer.parseInt(feeText);
                if (fee >= 500) {
                    pass++;  // Fee meets the condition
                }
                else {
                    fail++;  // Fee does not meet the condition
                }
            }
            else {
                fail++;  // Empty feeText is considered fail
            } 
        }
        System.out.println("pass count: "+pass);
        System.out.println("fail count: "+fail);
        return new int[]{pass, fail};  // Return counts of passed and failed fees
    }
}
