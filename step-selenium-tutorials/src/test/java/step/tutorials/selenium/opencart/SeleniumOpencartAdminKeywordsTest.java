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

import static org.junit.jupiter.api.Assertions.assertNull;

class SeleniumOpencartAdminKeywordsTest {

	private KeywordRunner.ExecutionContext ctx;

	@BeforeEach
	public void before() throws Exception {
		Map<String, String> properties = new HashMap<>();
		ctx = KeywordRunner.getExecutionContext(properties, SeleniumOpencartAdminKeywords.class);
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
				.add("username", "demo")
				.add("password", "demo")
				.build().toString();

		Output<JsonObject> output;
		output = ctx.run("Opencart RPA Testcase - Admin login", input);
		assertNull(output.getError());

		input = Json.createObjectBuilder()
				.add("product", "Canon EOS 5D")
				.add("quantity","999")
				.build().toString();

		ctx.run("Opencart RPA Testcase - Update Product", input);
		assertNull(output.getError());

		input = Json.createObjectBuilder()
				.add("product", "iPod Classic")
				.add("quantity","888")
				.build().toString();

		ctx.run("Opencart RPA Testcase - Update Product",input);
		assertNull(output.getError());

	}

}