package framework.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RentalcarScannerHomePage extends PageObject {

	public RentalcarScannerHomePage(WebDriver driver) {
		super();
		this.driver = driver;
	}

	@Override
	public RentalcarScannerHomePage go() {
		driver.get("http://rentalcar-scanner.com/");
		return this;
	}

	@Override
	public RentalcarScannerHomePage andWaitForPageToLoad() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("searchCarsFormBtn")));
		return this;
	}

	public boolean isBookingEngineSearchWidgetLoaded() {
		int secondsWait = 10;
		WebElement element = null;
		try {
			element = (new WebDriverWait(driver, secondsWait))
					.until(ExpectedConditions.elementToBeClickable(By.name("searchCarsFormBtn")));
		} catch (TimeoutException e) {
			System.out.println("searchCarsFormBtn not visible after " + secondsWait + "seconds");
		}
		return element != null;
	}

}
