package pomClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FinishpagePom {
	private WebDriver driver;

	public FinishpagePom(WebDriver driver) {
		this.driver = driver;
	}

	public String subheader() {
		return driver.findElement(By.className("subheader")).getText();
	}
	/**
	 * 
	 * @return the the heading shown.that is the thank you message
	 */
	public String title_header() {
		return driver.findElement(By.className("complete-header")).getText();
	}
	public String LastText() {
		return driver.findElement(By.className("complete-text")).getText();
	}
	
}
