package browserstack;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.browserstack.local.Local;

import static org.junit.Assert.*;
import framework.pageObjects.RentalcarScannerHomePage;

public class HomePageTests_Cloud {

	private static JSONObject config;

	private static String username;
	private static String accessKey;
	private static DesiredCapabilities capabilities;

	private WebDriver driver;
	private RentalcarScannerHomePage homePage;

	@BeforeClass
	public static void standUpTests() throws Exception {
		JSONParser parser = new JSONParser();
		config = (JSONObject) parser.parse(new FileReader("src/test/resources/single.conf.json"));
		username = (String) config.get("user");
		accessKey = (String) config.get("key");
		capabilities = new DesiredCapabilities();
		capabilities.setCapability("browser", "Chrome");
		capabilities.setCapability("browser_version", "80.0");
		capabilities.setCapability("os", "Windows");
		capabilities.setCapability("os_version", "10");
		capabilities.setCapability("resolution", "1024x768");
		capabilities.setCapability("name", "Bstack-[Java] Sample Test");
		
	}

	@Before
	public void setUp() throws MalformedURLException {
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
