package Pages;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;


import base.Base;

public class HomePage extends Base{
	
	 By city_list_path=By.xpath("//*[@id='c-omni-container']/div/div[1]/div[2]/div[2]/div");
	 By city_input=By.xpath("//*[@id=\"c-omni-container\"]/div/div[1]/div[1]/input"); 
	 By specalist=By.xpath("//div[@class=\"c-omni-suggestion-item\"]");
	 
	
		
		
	 
	 public void selectCity() throws Exception { 
		 WebElement input=driver.findElement(By.xpath("//*[@id='c-omni-container']/div/div[1]/div/input"));
    	 input.clear();
    	 input.sendKeys("Hyderabad");
    	 input.sendKeys(Keys.ARROW_DOWN);         Thread.sleep(1000);
    	 input.sendKeys(Keys.ARROW_DOWN);
    	 Thread.sleep(1000);
    	 input.sendKeys(Keys.ARROW_DOWN);
    	 input.sendKeys(Keys.ENTER);
	 }
	 
	 public int[] selectSpecality() throws Exception {
		 WebElement input=driver.findElement(By.xpath("//*[@id='c-omni-container']/div/div[2]/div[1]/input"));
		 input.sendKeys("dentist");
		 input.sendKeys(Keys.ARROW_DOWN);
		 input.sendKeys(Keys.ENTER);
		 System.out.println("before");
		 List<WebElement> specalist=driver.findElements(By.xpath("//*[@class=\"info-section\"]/div[1]/div/span"));
		 Thread.sleep(3000);
		 int pass=0,fail=0;
		 for(WebElement w:specalist) {
			 if(w.getText().contains("Dentist")) {
				 pass+=1;
			 }
			 else {
				 fail+=1;
			 }
		 }
		 
	     return new int[] {pass,fail};
	    
	 }

}

	 
