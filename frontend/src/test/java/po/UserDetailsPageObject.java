package po;

import org.openqa.selenium.WebDriver;

/**
 * Created by Stella on 11.08.2017.
 */
public class UserDetailsPageObject extends PageObject {

    public UserDetailsPageObject(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOnPage() {
        return driver.getTitle().contains("User details");
    }

    public String getUserId() {
        return getText("userId");
    }

}
