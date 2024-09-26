package Pages;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.aventstack.extentreports.Status;

import base.Base;

public class DoctorsPage extends Base {
	     
		//for Time and DoctorName xpaths
	    By dName=By.xpath("//*[@id='container']/div/div[4]/div/div[1]/div/div[3]/div[1]/div/div[1]/div[2]/a/div/h2");
	    By btn=By.xpath("//*[@id='container']/div/div[4]/div/div[1]/div/div[3]/div[1]/div/div[2]/div/div/div[2]/div[1]/button");
	    By slots_locator=By.xpath("//div[@data-qa-id='date_selector']");
	    By notAvailable_day=By.xpath("//div[@data-qa-id='date_selector']");
	    By timings_list=By.xpath("//div[@data-qa-id=\"slot_time\"]");
	    By afterName_locator=By.xpath("//*[@id='container']/div[2]/div/div[1]/div/div[1]/div[3]/div/div[2]/div[1]");
	    
	    //xpaths for date comparision
	    By after_date_locator=By.xpath("(//div[@class=\"pure-u-1-2\"])[2]/span[2]");
	    By dates_list_locator=By.xpath("//div[@data-qa-id='date_selector']/div[1]");
	    
	    //filtered consultation xpaths
	    By filt_btn=By.xpath("//i[@data-qa-id=\"all_filters_icon\"]");
	    By radio_btn=By.xpath("(//span[.=\"Above ₹500\"])[2]");
	    By filter_doctors=By.xpath("//div[@class=\"uv2-spacer--xs-top\"]/span[1]");
	    
	    public void doctorName_Time() throws Exception {
			WebElement doctorName=driver.findElement(dName);
			String beforeName=doctorName.getText();
			WebElement dctr_button=driver.findElement(btn);
		    dctr_button.click();
		    
	   		String available_slot_day="";
	   	    List<WebElement> date_list=driver.findElements(dates_list_locator);//today,tomorrow, other dates
	   		List<WebElement> slots=driver.findElements(slots_locator);//total container of every slot
	   		
	   		int i=0;
	   	    for(WebElement slot: slots) {
	   		     String text=slot.getText();
	   		     if(!text.contains("No")) {
	   			     slot.click();
	   			     available_slot_day=date_list.get(i).getText();
	   			     Thread.sleep(1000);
	   			     System.out.println("slot clicked");
	   			     break;	 
	   		     }
	   		     /*else {
	   			     String s=driver.findElement(notAvailable_day).getText();
	   			     System.out.println(s);
	   	         }*/
	   		     i++;
	   	    }
	   	    
	   	    //timings
	   	    List<WebElement> timings =driver.findElements(timings_list);
	   	    Thread.sleep(1000);
	   	    String before_time =timings.get(0).getText();
	   	    timings.get(0).click();	 
	   	    String after_time=driver.findElement(By.xpath("//*[@id='container']/div[2]/div/div[1]/div/div[1]/div[2]/div[1]/div[2]/span[2]")).getText();
	   	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);//time  formatter
                   // Parse both time strings to LocalTime
  	        LocalTime time1 = LocalTime.parse(before_time, timeFormatter);
	        LocalTime time2 = LocalTime.parse(after_time, timeFormatter);
	   	    System.out.println("Before time: "+time1);
	   	    System.out.println("After time: "+time2);
	   	    test=report.createTest("Validate Time");
	   	    if(time1!= null && time1.equals(time2)) {
	   	    	test.log(Status.PASS, "Both user selected Time and appointed Time are same");
	   	        System.out.println("Timings are same as before");
	   	    }
	   		  
	   	    else {
	   	    	test.log(Status.FAIL, "Both user selected Time and appointed Time are NOT  same");
	   	    	System.out.println("Timings are NOT same as before");
	   	    }
	   		   
	   	   
	   	   //Doctor Name
	   	   String afterName=driver.findElement(afterName_locator).getText();
	       System.out.println("before Doctor name: "+beforeName);
	   	   System.out.println("after Doctor name: "+afterName);
	   	   test=report.createTest("Validate Doctor Name");
		   if(beforeName.equals(afterName)) {
			   test.log(Status.PASS, "Both user selected Doctor Name and appointed Doctor Name are same");
			   System.out.println("before Doctor name is same as after");
		   }
			    
		   else {
			   test.log(Status.FAIL, "Both user selected Doctor Name and appointed Doctor Name are same ");
			   System.out.println("before Doctor name is NOT same as after");
		   } 
		   
		   //for Date
	   	   Thread.sleep(2000);
	   	   //for today
	       LocalDate today = LocalDate.now();
	       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
	       String todayDate = today.format(formatter);
           String before_date="";
           //for tomorrow
	       LocalDate tomorrow = today.plusDays(1);
	       String tomorrowDate = tomorrow.format(formatter);
	       //System.out.println("Today's Date: "+todayDate);
	       //System.out.println("Tomorrow's Date: "+tomorrowDate);
	       
	       String after_date=driver.findElement(after_date_locator).getText();
	       
	       //checking the slot date
	   	   if(available_slot_day.equals("Today")) 
	   		        before_date=todayDate;
	   	   else if(available_slot_day.equals("Tomorrow")) 
	   		      before_date =tomorrowDate;	    	
	   	   else 
	   		   before_date=available_slot_day.formatted(formatter);
	   	   
	   	   //date validation
	      	System.out.println("Before Date: "+before_date );
	      	System.out.println("After Date: "+after_date );
	   	    if(before_date.equals(after_date)) 
		    	System.err.println("selected date is same as appointed date");
		
		    else 
		    	System.err.println("selected date is NOT same as appointed date");   		 
	   }
	    
	   public int[] consultationFee() {	   
	        driver.findElement(filt_btn).click();
            driver.findElement(radio_btn).click();
            List<WebElement> filter_list=driver.findElements(filter_doctors);
            int a=0,pass=0,fail=0;
            for(WebElement w:filter_list) {
                   String feeText = w.getText().replaceAll("[^0-9]", "");//for keeping numerical values only
   	               if(a==0) 
   		                continue;
   	               a+=1;
    	           if (!feeText.isEmpty()) {
                        int fee = Integer.parseInt(feeText);
                        if (fee >= 500) 
                             pass += 1;  // Fee matches the condition
                        else 
                             fail += 1;  // Fee does not match the condition
                    } 
    	            else {
                          fail += 1; // If feeText is empty
                     }
             }
             int res[]= {pass,fail};
             return res;  		
        }

}