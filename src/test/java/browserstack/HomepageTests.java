package browserstack;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.*;

import framework.pageObjects.PageObject;
import framework.pageObjects.RentalcarScannerHomePage;

public class HomepageTests {

	private WebDriver driver;
	private RentalcarScannerHomePage homePage;

	@BeforeClass
	public static void standUpTests() {
		System.setProperty("webdriver.chrome.driver", "/Users/richardflynn/Documents/java/drivers/chromedriver");
	}

	@Before
	public void setupTest() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		homePage = new RentalcarScannerHomePage(driver);
		homePage.go().andWaitForPageToLoad();
	}

	@After
	public void tearDownTest() {
		driver.close();
	}

	@Test
	public void isBookingEngineSearchWidgetLoaded_Test() {
		assertTrue("BookingEngineSearchWidget not loaded", homePage.isBookingEngineSearchWidgetLoaded());
	}

	@Test
	public void title_Test() {
		assertEquals("incorrect title", homePage.getPageTitle(),
				"Find the best Car Rental Deals | Save up to 40% | rentalcar-scanner.com");
	}

	@Test
	public void metaDescription_Test() {
		assertEquals("incorrect meta description", homePage.getMetaDescription(),
				"rentalcar-scanner.com compares car rental from 900+ companies worldwide. "
						+ "Compare luxury, economy, and family car rental in 30,000+ locations. Best price guaranteed.");

	}

}
