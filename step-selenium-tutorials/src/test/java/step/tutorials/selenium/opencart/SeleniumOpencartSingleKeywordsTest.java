package step.tutorials.selenium.opencart;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import step.functions.io.Output;
import step.handlers.javahandler.KeywordRunner;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SeleniumOpencartSingleKeywordsTest {

	private KeywordRunner.ExecutionContext ctx;

	@BeforeEach
	public void before() throws Exception {
		Map<String, String> properties = new HashMap<>();
		ctx = KeywordRunner.getExecutionContext(properties, SeleniumOpencartSingleKeywords.class);
	}

	@AfterEach
	public void tearDown() {
		if (ctx != null) {
			ctx.close();
		}
	}

	@Test
	public void testSeleniumOpencartSingleKeywordsTest() throws Exception {
		String input = Json.createObjectBuilder()
				.add("headless", false)
				.build().toString();

		Output<JsonObject> output;
		output = ctx.run("Open Chrome", input);
		assertNull(output.getError());
		ctx.run("Opencart - Home Page");
		assertNull(output.getError());
		ctx.run("Opencart - Navigate to iMac Product");
		assertNull(output.getError());
		ctx.run("Opencart - Add product to cart");
		assertNull(output.getError());
		ctx.run("Opencart - View cart");
		assertNull(output.getError());
		ctx.run("Opencart - Start checkout");
		assertNull(output.getError());
		ctx.run("Opencart - Select guest mode");
		assertNull(output.getError());

		input = Json.createObjectBuilder()
				.add("firstname", "John")
				.add("lastname","Doe")
				.add("email","John@Doe.org")
				.build().toString();
		ctx.run("Opencart - Fill and submit billing  details",input);
		assertNull(output.getError());
		ctx.run("Opencart - Fill and submit shipping method");
		assertNull(output.getError());
		ctx.run("Opencart - Fill and submit payment method");
		assertNull(output.getError());
		ctx.run("Opencart - Confirm order");
		assertNull(output.getError());
	}

}