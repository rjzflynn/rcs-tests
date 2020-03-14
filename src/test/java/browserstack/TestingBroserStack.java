package browserstack;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.browserstack.local.Local;

//@RunWith(Parallelized.class)
public class TestingBroserStack {

	public WebDriver driver;
	private Local l;
	private static JSONObject config;

	@Parameter(value = 0)
	public int taskID;

	@Parameters
	public static Iterable<? extends Object> data() throws Exception {
		System.out.println("data");
		JSONParser parser = new JSONParser();
		config = (JSONObject) parser.parse(new FileReader("src/test/resources/single.conf.json"));
		int envs = ((JSONArray) config.get("environments")).size();

		List<Integer> taskIDs = new ArrayList<Integer>();
		for (int i = 0; i < envs; i++) {
			taskIDs.add(i);
		}

		return taskIDs;
	}

	@Before
	public void setUp() throws Exception {
		data();
		System.out.println("setup");

		JSONArray envs = (JSONArray) 
				config.get("environments");

		DesiredCapabilities capabilities = new DesiredCapabilities();

		Map<String, String> envCapabilities = (Map<String, String>) envs.get(taskID);
		Iterator it = envCapabilities.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
		}

		Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
		it = commonCapabilities.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (capabilities.getCapability(pair.getKey().toString()) == null) {
				capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
			}
		}

		String username = System.getenv("BROWSERSTACK_USERNAME");
		if (username == null) {
			username = (String) config.get("user");
		}

		String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
		if (accessKey == null) {
			accessKey = (String) config.get("key");
		}

		String app = System.getenv("BROWSERSTACK_APP_ID");
		if (app != null && !app.isEmpty()) {
			capabilities.setCapability("app", app);
		}

		if (capabilities.getCapability("browserstack.local") != null
				&& capabilities.getCapability("browserstack.local") == "true") {
			l = new Local();
			Map<String, String> options = new HashMap<String, String>();
			options.put("key", accessKey);
			l.start(options);
		}

		driver = new RemoteWebDriver(
				new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		if (l != null)
			l.stop();
	}

	@Test
	public void test() throws Exception {
		driver.get("https://www.google.com/ncr");
		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys("BrowserStack");
		element.submit();
		Thread.sleep(5000);

		assertEquals("BrowserStack - Google Search", driver.getTitle());
	}

}
