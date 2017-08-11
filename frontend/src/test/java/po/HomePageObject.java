package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

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

    public CreateItemPageObject toCreateItem() {
        if (!isLoggedIn()) {
            return null;
        }

        driver.findElement(By.id("createItem")).click();
        waitForPageToLoad();
        return new CreateItemPageObject(driver);
    }

    public UserDetailsPageObject toUserDetails() {
        if (!isLoggedIn()) {
            return null;
        }

        driver.findElement(By.id("userLink")).click();
        waitForPageToLoad();
        return new UserDetailsPageObject(driver);
    }

    public ItemPageObject toItemDetails() {

        driver.findElement(By.id("itemLink")).click();
        waitForPageToLoad();
        return new ItemPageObject(driver);
    }

    public int getNumberOfDisplayedItems(){
        List<WebElement> elements = driver.findElements(By.xpath("//table[@id='itemTable']//tbody//tr[string-length(text()) > 0]"));
        return elements.size();

    }

    public int getNumberOfDisplayedItemsByUser(String userId){
        List<WebElement> elements = driver.findElements(
                By.xpath("//table[@id='itemTable']//tbody//tr[contains(td[1], '" + userId + "')]"));
        return elements.size();
    }

    public void setShowOnlyOwnItems(boolean value){
        List<WebElement> elements = driver.findElements(By.id("showOnlyOwnItemsForm:showOnlyOwnItems"));
        WebElement e = elements.get(0);

        if ((value && !e.isSelected()) || (!value && e.isSelected())) {
            e.click();
            waitForPageToLoad();
        }
    }

    public String getItemText(int position){
        return getText("itemTable:"+position+":text");
    }





}
