package framework.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class PageObject {

	protected WebDriver driver;

	public abstract PageObject go();
	
	public abstract PageObject andWaitForPageToLoad();

	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getMetaDescription() {
		int secondsWait = 10;
		WebElement element = null;
		try {
			element = (new WebDriverWait(driver, secondsWait))
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//meta[@name='description']")));
		} catch (TimeoutException e) {
			System.out.println("metaDesc not visible after " + secondsWait + "seconds");
		}
		if (element != null) {
			return element.getAttribute("content");
		} else {
			return null;
		}
	
	}

}
