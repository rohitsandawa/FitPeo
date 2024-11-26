package testcases;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import base.basetest;

public class FitPeoAutomation extends basetest {
    @Test
	public void FitPeotest() {
		try {
			// 2. Navigate to the Revenue Calculator Page
			driver.findElement(By.xpath(config.getProperty("revcal"))).click();

			// 3. Scroll Down to the Slider section
			WebElement patientslider = driver.findElement(By.xpath(config.getProperty("patientslider")));
			WebElement patienttextbox = driver.findElement(By.xpath(config.getProperty("patientxtbox")));
			Actions action = new Actions(driver);

			// 4. Adjust the Slider and Verify its value with the patient textbox
			action.clickAndHold(patientslider).build().perform();
			String PatientBoxValue = patienttextbox.getAttribute("value");
			int targetValue = 820; 

			while (Integer.parseInt(PatientBoxValue) != targetValue - 1) {
				PatientBoxValue = patienttextbox.getAttribute("value");
				action.sendKeys(Keys.ARROW_RIGHT).build().perform();
			}

			if (Integer.parseInt(PatientBoxValue) == targetValue - 1) {
				System.out.println("Patient box value updated to " + (Integer.parseInt(PatientBoxValue) + 1));
			} else {
				System.out.println("Patient box value should be " + targetValue + " but it is showing "
						+ (Integer.parseInt(PatientBoxValue) + 1));
			}

			// 5. Update the Text Field
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement patientbox = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(config.getProperty("patientxtbox"))));

			patientbox.sendKeys(Keys.CONTROL + "a");
			patientbox.sendKeys(Keys.DELETE);
			patientbox.sendKeys("560");

			// 6. Verify patient slider with textbox
			int patientslidervalue = Integer.parseInt(patientslider.getAttribute("value"));
			if (patientslidervalue == 560) {
				System.out.println("Patient Slider value is " + patientslidervalue);
			}

			// 7. Selecting the checkboxes
			WebElement cpt99001 = driver.findElement(By.xpath(config.getProperty("CPT-99091")));
			WebElement cpt99453 = driver.findElement(By.xpath(config.getProperty("CPT-99453")));
			WebElement cpt99454 = driver.findElement(By.xpath(config.getProperty("CPT-99454")));
			WebElement cpt99474 = driver.findElement(By.xpath(config.getProperty("CPT-99474")));

			if (!cpt99001.isSelected())
				cpt99001.click();
			if (!cpt99453.isSelected())
				cpt99453.click();
			if (!cpt99454.isSelected())
				cpt99454.click();
			if (!cpt99474.isSelected())
				cpt99474.click();

			// 8. Validate the reimbursement
			patientbox.sendKeys(Keys.CONTROL + "a");
			patientbox.sendKeys(Keys.DELETE);
			patientbox.sendKeys("820");
			System.out.println("Final value of patient slider is "+ Integer.parseInt(patientbox.getAttribute("value")));

			// 9. Verify total recurring reimbursement at the header
			action.scrollToElement(cpt99474).build().perform();
			WebElement total_reimburse = driver.findElement(By.xpath(config.getProperty("totalreimburse")));
			String Actualtotal = total_reimburse.getText();
			String Expectedtotal = "$110700";

			if (Actualtotal.equals(Expectedtotal)) {
				System.out.println("Total Recurring Reimbursement for all Patients Per Month: " + Actualtotal);
			} else {
				System.out.println("Test Failed: Expected '" + Expectedtotal + "' but found '" + Actualtotal + "'");
			}

		} catch (NoSuchElementException e) {
			System.out.println("Error: Element not found. " + e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println("Error: Invalid number format. " + e.getMessage());
		} catch (Exception e) {
			System.out.println("An unexpected error occurred: " + e.getMessage());
		} 
	}
}

