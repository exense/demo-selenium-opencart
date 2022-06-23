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

public class SeleniumOpencartTestcaseAsKeywordTest {

	private KeywordRunner.ExecutionContext ctx;

	@BeforeEach
	public void before() throws Exception {
		Map<String, String> properties = new HashMap<>();
		ctx = KeywordRunner.getExecutionContext(properties, SeleniumOpencartTestcaseAsKeyword.class);
	}

	@Test
	public void testOpencartTestcaseAsKeyword() throws Exception {
		String input = Json.createObjectBuilder()
				.add("headless", false)
				.build().toString();
		Output<JsonObject> output = ctx.run("Opencart Testcase as Keyword", input);
		assertNull(output.getError());
	}

	@AfterEach
	public void tearDown() {
		if (ctx != null) {
			ctx.close();
		}
	}
}