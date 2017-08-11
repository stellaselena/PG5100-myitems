package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Stella on 11.08.2017.
 */
public class HomePageObject extends PageObject{

    public HomePageObject(WebDriver driver) {
        super(driver);
    }

    public HomePageObject toStartingPage() {
        String context = "/my_items"; // see jboss-web.xml
        driver.get("localhost:8080" + context + "/home.jsf");
        waitForPageToLoad();

        return this;
    }

    public boolean isOnPage() {
        return driver.getTitle().equals("My Items Home Page");
    }

    public LoginPageObject toLogin() {
        if (isLoggedIn()) {
            logout();
        }

        driver.findElement(By.id("login")).click();
        waitForPageToLoad();
        return new LoginPageObject(driver);
    }
}
