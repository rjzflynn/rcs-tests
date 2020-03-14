package browserstack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import framework.pageObjects.RentalcarScannerHomePage;

@RunWith(Parallelized.class)
public class HomePageTests_Cloud {

	private static JSONObject config;

	@Parameter(value = 0)
	public static int taskID;

	@Parameters
	public static Iterable<? extends Object> data() throws Exception {
		JSONParser parser = new JSONParser();
		config = (JSONObject) parser.parse(new FileReader("src/test/resources/conf.json"));
		int envs = ((JSONArray) config.get("environments")).size();

		List<Integer> taskIDs = new ArrayList<Integer>();
		for (int i = 0; i < envs; i++) {
			taskIDs.add(i);
		}
		return taskIDs;
	}

	private DesiredCapabilities capabilities;
	private WebDriver driver;
	private RentalcarScannerHomePage homePage;

	@BeforeClass
	public static void setupConfig() throws Exception {
		JSONParser parser = new JSONParser();
		config = (JSONObject) parser.parse(new FileReader("src/test/resources/conf.json"));
	}

	
	@Before
	public void setUp() throws MalformedURLException {
		capabilities = new DesiredCapabilities();	
		JSONArray envs = (JSONArray) config.get("environments");
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

		String username = (String) config.get("user");
		String accessKey = (String) config.get("key");

		driver = new RemoteWebDriver(
				new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
		homePage = new RentalcarScannerHomePage(driver);
		homePage.go().andWaitForPageToLoad();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	final String PAGE_TITLE = "Find the best Car Rental Deals | Save up to 40% | rentalcar-scanner.com";
	final String PAGE_META_DESC = "rentalcar-scanner.com compares car rental from 900+ companies worldwide. "
			+ "Compare luxury, economy, and family car rental in 30,000+ locations. Best price guaranteed.";

	@Test
	public void isBookingEngineSearchWidgetLoaded_Test() {
		assertTrue("BookingEngineSearchWidget not loaded", homePage.isBookingEngineSearchWidgetLoaded());
	}

	@Test
	public void title_Test() {
		assertEquals("incorrect title", homePage.getPageTitle(), PAGE_TITLE);
	}

	@Test
	public void metaDescription_Test() {
		assertEquals("incorrect meta description", homePage.getMetaDescription(), PAGE_META_DESC);

	}

}
