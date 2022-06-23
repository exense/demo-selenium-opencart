package step.tutorials.selenium.opencart;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import step.handlers.javahandler.AbstractKeyword;
import step.handlers.javahandler.Keyword;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

import static step.tutorials.selenium.opencart.SeleniumHelper.*;

public class SeleniumOpencartSingleKeywords extends AbstractKeyword {

	@Keyword(name = "Open Chrome",
			schema = "{\"properties\":{\"headless\":{\"type\":\"boolean\"}}, \"required\":[]}")
	public void openChrome() {
		WebDriver driver = createDriver(input.getBoolean("headless", true));
		//the driver is stored in session to be reused by the other single keywords
		//A wrapper implementing Closeable is used to automatically close the driver/browser after each session
		this.session.put(new DriverWrapper(driver));

	}

	@Keyword(name = "Opencart - Home Page")
	public void opencartHomePage() throws Exception {
		WebDriver driver = getNonNullDriverFromSession();
		//Navigate to opencart website
		driver.get("https://opencart-prf.exense.ch/");
	}

	@Keyword(name = "Opencart - Navigate to iMac Product")
	public void opencartNavigateToProduct() throws Exception {
		WebDriver driver = getNonNullDriverFromSession();
		//Navigate to iMac product page
		driver.findElement(By.xpath("//a[text()='Desktops']")).click();
		driver.findElement(By.xpath("//a[text()='Show All Desktops']")).click();
		driver.findElement(By.xpath("//a[contains(@class,'list-group-item') and contains(text(),'Mac')]")).click();
		driver.findElement(By.xpath("//a[text()='iMac']")).click();
	}

	@Keyword(name = "Opencart - Add product to cart")
	public void opencartAddProductToCart() throws Exception {
		WebDriver driver = getNonNullDriverFromSession();
		//Add product to cart
		driver.findElement(By.xpath("//button[text()='Add to Cart']")).click();
	}

	@Keyword(name = "Opencart - View cart")
	public void opencartViewCart() throws Exception {
		WebDriver driver = getNonNullDriverFromSession();
		//display card popup
		driver.findElement(By.xpath("//div[@id='cart']/button")).click();
		//view cart
		driver.findElement(By.xpath("//a/strong[text()=' View Cart']")).click();
	}

	@Keyword(name = "Opencart - Start checkout")
	public void opencartStartCheckout() throws Exception {
		WebDriver driver = getNonNullDriverFromSession();
		//start checkout
		driver.findElement(By.xpath("//a[text()='Checkout']")).click();
	}

	@Keyword(name = "Opencart - Select guest mode")
	public void opencartSSelectGuestMode() throws Exception {
		WebDriver driver = getNonNullDriverFromSession();
		//Checkout options
		driver.findElement(By.xpath("//input[@value='guest']")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(IMPLICIT_WAIT));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("button-account"))).click();
	}

	@Keyword(name = "Opencart - Fill and submit billing  details",
			schema = "{\"properties\":{" +
					"\"firstname\":{\"type\":\"string\"}, " +
					"\"lastname\":{\"type\":\"string\"}, " +
					"\"email\":{\"type\":\"string\"}, " +
					"\"telephone\":{\"type\":\"string\"}, " +
					"\"address\":{\"type\":\"string\"}, " +
					"\"city\":{\"type\":\"string\"}, " +
					"\"postcode\":{\"type\":\"string\"}" +
					"}, \"required\":[]}")
	public void opencartSubmitBillingDetails() throws Exception {
		WebDriver driver = getNonNullDriverFromSession();
		//Fill Billing details
		driver.findElement(By.id("input-payment-firstname")).sendKeys(input.getString("firstname", "Gustav"));
		driver.findElement(By.id("input-payment-lastname")).sendKeys(input.getString("lastname", "Muster"));
		driver.findElement(By.id("input-payment-email")).sendKeys(input.getString("email", "gustav@muster.ch"));
		driver.findElement(By.id("input-payment-telephone")).sendKeys(input.getString("telephone", "+41777777777"));
		driver.findElement(By.id("input-payment-address-1")).sendKeys(input.getString("address", "Bahnhofstrasse 1"));
		driver.findElement(By.id("input-payment-city")).sendKeys(input.getString("city", "Zurich"));
		driver.findElement(By.id("input-payment-postcode")).sendKeys(input.getString("postcode", "8001"));
		Select selectCountry = new Select(driver.findElement(By.id("input-payment-country")));
		selectCountry.selectByVisibleText("Switzerland");
		Select selectZone = new Select(driver.findElement(By.id("input-payment-zone")));
		selectZone.selectByVisibleText("Zürich");
		//Submit billing details
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(IMPLICIT_WAIT));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("button-guest"))).click();
	}

	@Keyword(name = "Opencart - Fill and submit shipping method")
	public void opencartSubmitShippingMethod() throws Exception {
		WebDriver driver = getNonNullDriverFromSession();
		//validate delivery mode
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(IMPLICIT_WAIT));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("button-shipping-method"))).click();
	}

	@Keyword(name = "Opencart - Fill and submit payment method")
	public void opencartSubmitPaymentMethod() throws Exception {
		WebDriver driver = getNonNullDriverFromSession();
		//agree to terms and validate payment method
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(IMPLICIT_WAIT));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='agree']"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("button-payment-method"))).click();
	}

	@Keyword(name = "Opencart - Confirm order")
	public void opencartConfirmOrder() throws Exception {
		WebDriver driver = getNonNullDriverFromSession();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(IMPLICIT_WAIT));
		//Confirm order
		wait.until(ExpectedConditions.elementToBeClickable(By.id("button-confirm"))).click();
		//Final content check
		driver.findElement(By.xpath("//h1[text()='Your order has been placed!']"));
	}

	/*
	* Override the onError method to control how Selenium exceptions are reported in step
	* It will also take a screenshot of the browser and add it to the report
	 */
	@Override
	public boolean onError(Exception e) {
		return onErrorHandler(e, getDriverFromSession(), output);
	}

	protected WebDriver getNonNullDriverFromSession() throws Exception {
		return getDriverFromSession().orElseThrow(()
		-> new Exception("Please first execute keyword \"Open Chome\" in order to have a driver available for this keyword"));
	}

	protected Optional<WebDriver> getDriverFromSession() {
		DriverWrapper driverWrapper = session.get(DriverWrapper.class);
		return (driverWrapper!=null) ?
				Optional.of(driverWrapper.driver) :
				Optional.empty();
	}



	// Wrapping the WebDriver instance to put it to the Session
	// as it is not implementing the Closeable interface
	public class DriverWrapper implements Closeable {

		final WebDriver driver;

		public DriverWrapper(WebDriver driver) {
			super();
			this.driver = driver;
		}

		@Override
		public void close() throws IOException {
			driver.quit();
		}
	}
}