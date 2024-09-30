package Pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import base.Base;

public class HomePage extends Base {

    // Locators for city and specialist inputs
    By city_input_locator = By.xpath("//*[@id='c-omni-container']/div/div[1]/div/input");
    By specality_input_locator = By.xpath("//*[@id='c-omni-container']/div/div[2]/div[1]/input");
    By specalist_list_locator = By.xpath("//*[@class=\"info-section\"]/div[1]/div/span");

    // Method to select a city based on user input
    public void selectCity() throws Exception {
        WebElement input = driver.findElement(city_input_locator);
        input.clear(); // Clear previous input field text
        Thread.sleep(2000); //waiting 2 seconds to load
        
        // Send the city name (from .properties file)
        input.sendKeys(prop.getProperty("city"));
        
        // Navigate the dropdown options using arrow keys and select the city
        for(int i=1;i<=3;i++) {
            input.sendKeys(Keys.ARROW_DOWN);
            Thread.sleep(1000); // dropdown options load properly
        }
        input.sendKeys(Keys.ENTER); 
        Thread.sleep(1000); 
    }

    // Method to select a specialty (like 'Dentist') 
    public int[] selectSpecality() throws Exception {
        WebElement input = driver.findElement(specality_input_locator);
        
        // Send the specialist type (from .properties file)
        input.sendKeys(prop.getProperty("specialist"));
        input.sendKeys(Keys.ARROW_DOWN); 
        input.sendKeys(Keys.ENTER); 
        Thread.sleep(2000); 
        
        // Get the list of displayed specialists
        List<WebElement> specalists = driver.findElements(specalist_list_locator);
        
        // Counters to keep track of valid ('Dentist') and invalid results
        int pass = 0, fail = 0;
        for (WebElement w : specalists) {
            if (w.getText().contains("Dentist")) {
                pass += 1; // Increment if the specialist is 'Dentist'
            } else {
                fail += 1; // Increment if the specialist is not a'Dentist'  
            }
        }

        // Return the number of 'Dentist' specalists as pass and others (fail)
        return new int[] {pass, fail};
    }

}
