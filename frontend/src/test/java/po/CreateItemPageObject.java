package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.assertEquals;

/**
 * Created by Stella on 11.08.2017.
 */
public class CreateItemPageObject extends PageObject {

    public CreateItemPageObject(WebDriver driver){
        super(driver);
        assertEquals("Create New Item", driver.getTitle());

    }

    public boolean isOnPage(){
        return driver.getTitle().equals("Create New Item");
    }

    public HomePageObject createItem(String title, String type, String description){
        setText("createItemForm:title",title);
        setText("createItemForm:description",description);

        new Select(driver.findElement(By.id("createItemForm:type"))).selectByVisibleText(type);

        driver.findElement(By.id("createItemForm:createButton")).click();
        waitForPageToLoad();

        if(isOnPage()){
            return null;
        } else {
            return new HomePageObject(driver);
        }
    }


}
