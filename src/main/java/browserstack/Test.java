package browserstack;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.pageObjects.RentalcarScannerHomePage;

public class Test {

	public static void main(String[] args) {

		// setting the driver executable
		System.setProperty("webdriver.chrome.driver", "/Users/richardflynn/Documents/java/drivers/chromedriver");

		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		driver.get("http://rentalcar-scanner.com/");

		RentalcarScannerHomePage homePage = new RentalcarScannerHomePage(driver);

		System.out.println(homePage.isBookingEngineSearchWidgetLoaded());

		System.out.println(homePage.getPageTitle()
				.equals("Find the best Car Rental Deals | Save up to 40% | rentalcar-scanner.com"));

		System.out.println(homePage.getMetaDescription().equals(
				"rentalcar-scanner.com compares car rental from 900+ companies worldwide. "
						+ "Compare luxury, economy, and family car rental in 30,000+ locations. Best price guaranteed."));

		driver.close();


	}

}
