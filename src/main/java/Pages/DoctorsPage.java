package Pages;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import com.aventstack.extentreports.Status;

import base.Base;

public class DoctorsPage extends Base {
	
	String appointedDate;
	String displayedCityName;
	String selectedcityName;
	String userSelectedTime;
	LocalDate todayDate= LocalDate.now();
	LocalDate tomorrowDate= todayDate.plusDays(1);
	String selectedName;
	String appointedName;
	String appointedTimeSlot;
	String selectedFees;
	
	 By todaySlotPath=By.xpath("//*[@id=\"container\"]/div/div[4]/div/div[1]/div/div[3]/div[2]/div/div/div/div[2]/div[1]/div[2]/div[1]/div[2]/span");
	 By todayTimePath=By.xpath("//*[@id=\"container\"]/div/div[4]/div/div[1]/div/div[2]/div[2]/div/div/div/div[2]/div[2]/div[1]/div[2]/div[1]");
		By tomorrowSlotPath=By.xpath("//*[@id=\"container\"]/div/div[4]/div/div[1]/div/div[3]/div[2]/div/div/div/div[2]/div[1]/div[2]/div[2]");
		By tomorrowTimePath=By.xpath("//*[@id=\"container\"]/div/div[4]/div/div[1]/div/div[3]/div[2]/div/div/div/div[2]/div[2]/div[1]/div[2]/div[1]");
		By selectedNamePath=By.xpath("//*[@id=\"container\"]/div/div[4]/div/div[1]/div/div[3]/div[1]/div/div[1]/div[2]/a/div/h2");
		By appointedNamePath=By.xpath("//*[@id=\"container\"]/div[2]/div/div[1]/div/div[1]/div[3]/div/div[2]/div[1]");
		By appointedDatePath=By.xpath("//*[@id=\"container\"]/div[2]/div/div[1]/div/div[1]/div[2]/div[1]/div[1]/span[2]");
		By appointedTimePath=By.xpath("//*[@id=\"container\"]/div[2]/div/div[1]/div/div[1]/div[2]/div[1]/div[2]/span[2]");
		By filterDropdownPath=By.xpath("//*[@id=\"container\"]/div/div[3]/div/div/header/div[1]/div/div[4]");
		By feesPath=By.xpath("//*[@id=\"container\"]/div/div[3]/div/div/header/div[2]/div/div[2]/div/label[4]");
		By allfeesPath=By.xpath("//div[@class=\"uv2-spacer--xs-top\"]/child::span[1]");
	 
	 public void doctorName_Time() throws Exception {
			WebElement doctorName=driver.findElement(By.xpath("//*[@id='container']/div/div[4]/div/div[1]/div/div[3]/div[1]/div/div[1]/div[2]/a/div/h2"));
			String beforeName=doctorName.getText();
			WebElement button=driver.findElement(By.xpath("//*[@id='container']/div/div[4]/div/div[1]/div/div[3]/div[1]/div/div[2]/div/div/div[2]/div[1]/button"));
	   		button.click();
	   		
	   		List<WebElement> slots=driver.findElements(By.xpath("//div[@data-qa-id='date_selector']"));
	   	    for(WebElement slot: slots) {
	   		  String text=slot.getText();
	   		  if(!text.contains("No")) {
	   			 slot.click();
	   			 Thread.sleep(1000);
	   			 System.out.println("slot clicked");
	   			 break;	 
	   		  }
	   		  else {
	   			 String s=driver.findElement(By.xpath("//div[@data-qa-id='date_selector']")).getText();
	   			 System.out.println(s);
	   	      }
	   	    }
	   	    
	   	   //timings
	   	   List<WebElement> timings =driver.findElements(By.xpath("//div[@data-qa-id=\"slot_time\"]"));
	   	   Thread.sleep(1000);
	   	   String before_time =timings.get(0).getText();
	   	   timings.get(0).click();	 
	   	   String after_time=driver.findElement(By.xpath("//*[@id='container']/div[2]/div/div[1]/div/div[1]/div[2]/div[1]/div[2]/span[2]")).getText();
	   	   System.out.println("before time: "+before_time);
	   	   System.out.println("after time: "+after_time);
	   	   if(before_time != null && before_time.equals(after_time))
	   		   System.out.println("Timings are same");
	   	   else
	   		   System.out.println("Timings are not same");
	   	   
	   	   //Doctor Name
	   	   
	   	   String afterName=driver.findElement(By.xpath("//*[@id='container']/div[2]/div/div[1]/div/div[1]/div[3]/div/div[2]/div[1]")).getText();
	       System.out.println("before Doctor name: "+beforeName);
	   	   System.out.println("after Doctor name: "+afterName);
		   if(beforeName==afterName)
			    System.out.println("before Doctor name is same as after");
		   else
			    System.out.println("before Doctor name is NOT same as after");
		   
		   
		   
		   appointedDate=driver.findElement(appointedDatePath).getText();
			SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
	        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = inputFormat.parse(appointedDate);
	        
	        String convertedAppointedDate = outputFormat.format(date);
	        System.out.println("appointed date:"+convertedAppointedDate);
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust the pattern as needed
	        
	        // Convert LocalDate to String
	        String convertedSelectedDate = tomorrowDate.format(formatter);
	        
	      	System.out.println("selected date:"+convertedSelectedDate);
	      	test=report.createTest("Validate date");
	        if(convertedAppointedDate.trim().equals(convertedSelectedDate.trim())) {
	        	System.out.println("seleted date :"+convertedSelectedDate+"appointed date:"+convertedAppointedDate);
				System.out.println("Both selected date and appointed date are same");
	        	test.log(Status.PASS, "Both user selected date and appointed date are same");
	        	
	        }
	        else
	        {	
	        	
	        	test.log(Status.FAIL, "Both  user selected date and appointed date are not same");
			}
	        
	   	   Thread.sleep(5000);
	   		
	 }
	 public int[] consultationFee() {	   
	  
      driver.findElement(By.xpath("//i[@data-qa-id=\"all_filters_icon\"]")).click();
      driver.findElement(By.xpath("(//span[.=\"Above ₹500\"])[2]")).click();
      List<WebElement> filter_list=driver.findElements(By.xpath("//div[@class=\"uv2-spacer--xs-top\"]/span[1]"));
      int a=0,pass=0,fail=0;
      for(WebElement w:filter_list) {
   	   if(a==0) {
   		   continue;
   	   }
   	   a+=1;
   	   if(w.getText().contains("500"))
   	   {
   		   pass+=1;
   	   }
   	   else {
   		   fail+=1;
   	   }
      }
      int res[]= {pass,fail};
      return res; 
      
      
       		
	   }
	
	

}