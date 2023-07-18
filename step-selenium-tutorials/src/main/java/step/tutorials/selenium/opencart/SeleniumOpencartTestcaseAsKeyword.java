package step.tutorials.selenium.opencart;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import step.handlers.javahandler.AbstractKeyword;
import step.handlers.javahandler.Keyword;

import static step.tutorials.selenium.opencart.SeleniumHelper.*;

public class SeleniumOpencartTestcaseAsKeyword extends AbstractKeyword {

	private WebDriver driver;
	private static int SLEEP_TIME_MS = 100;

	@Keyword(name = "Opencart Testcase as Keyword",
			schema = "{\"properties\":{\"headless\":{\"type\":\"boolean\"},"+
					"\"sleepTimeMs\":{\"type\":\"number\"}, " +
					"\"firstname\":{\"type\":\"string\"}, " +
					"\"lastname\":{\"type\":\"string\"}, " +
					"\"email\":{\"type\":\"string\"}, " +
					"\"telephone\":{\"type\":\"string\"}, " +
					"\"address\":{\"type\":\"string\"}, " +
					"\"city\":{\"type\":\"string\"}, " +
					"\"postcode\":{\"type\":\"string\"}" +
					"}, \"required\":[]}")
	public void opencartTestcaseAsKeyword() throws Exception {
		driver = createDriver(input.getBoolean("headless", true));
		int sleepTimeMs = input.getInt("sleepTimeMs", SLEEP_TIME_MS);
		//Navigate to opencart website
		driver.get("https://opencart-prf.exense.ch/");
		//Navigate to iMac product page
		driver.findElement(By.xpath("//a[text()='Desktops']")).click();
		driver.findElement(By.xpath("//a[text()='Show All Desktops']")).click();
		driver.findElement(By.xpath("//a[contains(@class,'list-group-item') and contains(text(),'Mac')]")).click();
		driver.findElement(By.xpath("//a[text()='iMac']")).click();
		//Add product to cart
		driver.findElement(By.xpath("//button[text()='Add to Cart']")).click();
		//display card popup
		driver.findElement(By.xpath("//div[@id='cart']/button")).click();
		//view cart
		driver.findElement(By.xpath("//a/strong[text()=' View Cart']")).click();
		//start checkout
		driver.findElement(By.xpath("//a[text()='Checkout']")).click();
		//Checkout options
		driver.findElement(By.xpath("//input[@value='guest']")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(IMPLICIT_WAIT));
		TimeUnit.MILLISECONDS.sleep(sleepTimeMs); //force sleep as jquery not always attached to button
		wait.until(ExpectedConditions.elementToBeClickable(By.id("button-account"))).click();
		//Fill Billing details
		driver.findElement(By.id("input-payment-firstname")).sendKeys(input.getString("firstname","Gustav"));
		driver.findElement(By.id("input-payment-lastname")).sendKeys(input.getString("lastname","Muster"));
		driver.findElement(By.id("input-payment-email")).sendKeys(input.getString("email","gustav@muster.ch"));
		driver.findElement(By.id("input-payment-telephone")).sendKeys(input.getString("telephone","+41777777777"));
		driver.findElement(By.id("input-payment-address-1")).sendKeys(input.getString("address","Bahnhofstrasse 1"));
		driver.findElement(By.id("input-payment-city")).sendKeys(input.getString("city","Zurich"));
		driver.findElement(By.id("input-payment-postcode")).sendKeys(input.getString("postcode","8001"));
		Select selectCountry = new Select(driver.findElement(By.id("input-payment-country")));
		selectCountry.selectByVisibleText("Switzerland");
		Select selectZone = new Select(driver.findElement(By.id("input-payment-zone")));
		selectZone.selectByVisibleText("Zürich");
		//Submit billing details
		TimeUnit.MILLISECONDS.sleep(sleepTimeMs); //force sleep as jquery not always attached to button
		wait.until(ExpectedConditions.elementToBeClickable(By.id("button-guest"))).click();
		//validate delivery mode
		TimeUnit.MILLISECONDS.sleep(sleepTimeMs); //force sleep as jquery not always attached to button
		wait.until(ExpectedConditions.elementToBeClickable(By.id("button-shipping-method"))).click();
		//agree to terms and validate payment method
		TimeUnit.MILLISECONDS.sleep(sleepTimeMs); //force sleep as jquery not always attached to button
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='agree']"))).click();
		driver.findElement(By.id("button-payment-method")).click();
		//Confirm order
		TimeUnit.MILLISECONDS.sleep(sleepTimeMs); //force sleep as jquery not always attached to button
		wait.until(ExpectedConditions.elementToBeClickable(By.id("button-confirm"))).click();
		//Final content check
		driver.findElement(By.xpath("//h1[text()='Your order has been placed!']"));

		driver.quit();
	}

	/*
	 * Optional: Override the onError method to control how Selenium exceptions are reported in step
	 * It will also take a screenshot of the browser and add it to the report
	 */
	@Override
	public boolean onError(Exception e) {
		return onErrorHandler(e, Optional.ofNullable(driver), output);
	}
}