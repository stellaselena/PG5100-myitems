package po;

import controller.ItemController;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by Stella on 11.08.2017.
 */
public class ItemPageObject extends PageObject {

    public ItemPageObject(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOnPage() {
        return driver.getTitle().equals("Item Details");
    }

    public void selectItemsByType(ItemController.Types type){
        new Select(driver.findElement(By.id("statisticForm:statistic"))).selectByVisibleText(type.toString());
    }

    public int getItemTypeCount(){
        return Integer.parseInt(getText("count"));
    }

    public void selectItemsByCollection(ItemController.Collection collection){
        new Select(driver.findElement(By.id("categoryForm:category"))).selectByVisibleText(collection.toString());
    }

    public int getNumberOfDisplayedItems(){
        List<WebElement> elements = driver.findElements(By.xpath("//table[@id='itemTable']//tbody//tr[string-length(text()) > 0]"));
        return elements.size();

    }


}
