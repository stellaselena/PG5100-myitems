import controller.ItemController;
import org.junit.Test;
import po.CreateItemPageObject;
import po.ItemPageObject;

import static org.junit.Assert.*;

/**
 * Created by Stella on 11.08.2017.
 */
public class MyItemsIT extends WebTestBase {

    @Test
    public void testCreateOneItem(){
        String userId = getUniqueId();
        createAndLogNewUser(userId);

        home.setShowOnlyOwnItems(true);
        int n = home.getNumberOfDisplayedItems();

        String title = getUniqueTitle();
        CreateItemPageObject cipo = home.toCreateItem();
        home = cipo.createItem(title, "Car", "Honda Civic");

        assertNotNull(home);
        int x = home.getNumberOfDisplayedItems();
        assertEquals(n + 1, x);
        assertTrue(getPageSource().contains(title));

    }


    @Test
    public void testCreateItemsFromDifferentUsers(){
        String userId1 = getUniqueId();
        createAndLogNewUser(userId1);

        home.setShowOnlyOwnItems(false);
        int all = home.getNumberOfDisplayedItems();

        home.setShowOnlyOwnItems(true);
        int userOnly = home.getNumberOfDisplayedItems();

        String title1 = getUniqueTitle();
        CreateItemPageObject cipo = home.toCreateItem();
        home = cipo.createItem(title1, "Car", "Honda Civic");
        String title2 = getUniqueTitle();
        cipo = home.toCreateItem();
        home = cipo.createItem(title2, "Car", "Honda Civic");

        home.setShowOnlyOwnItems(true);
        assertEquals(userOnly + 2, home.getNumberOfDisplayedItems());

        home.setShowOnlyOwnItems(false);
        assertEquals(all + 2, home.getNumberOfDisplayedItems());

        home.logout();

        String userId2 = getUniqueId();
        createAndLogNewUser(userId2);

        String title3 = getUniqueTitle();
        cipo = home.toCreateItem();
        home = cipo.createItem(title3, "Car", "Honda Civic");

        home.setShowOnlyOwnItems(true);
        assertEquals(userOnly + 1, home.getNumberOfDisplayedItems());

        home.setShowOnlyOwnItems(false);
        assertEquals(all + 3, home.getNumberOfDisplayedItems());

    }

    @Test
    public void testGetNumberOfPostsMadeByUser(){
        String userId1 = getUniqueId();

        createAndLogNewUser(userId1);

        assertEquals(0, home.getNumberOfDisplayedItemsByUser(userId1));

        String title1 = getUniqueTitle();
        CreateItemPageObject cipo = home.toCreateItem();
        home = cipo.createItem(title1, "Car", "Honda Civic");
        assertEquals(1, home.getNumberOfDisplayedItemsByUser(userId1));

        home.logout();
        loginExistingUser(userId1);
        assertEquals(1, home.getNumberOfDisplayedItemsByUser(userId1));
        cipo = home.toCreateItem();
        home = cipo.createItem(title1, "Car", "Honda Civic");
        assertEquals(2, home.getNumberOfDisplayedItemsByUser(userId1));

    }

    @Test
    public void testLongDescription(){

        String text ="0123456789012345678901234567890123456789012345678901234567890123456789";
        assertTrue(text.length() > 30);

        createAndLogNewUser(getUniqueId());
        CreateItemPageObject cipo = home.toCreateItem();
        home = cipo.createItem("Honda", "Car", text);

        home.setShowOnlyOwnItems(true);
        int position = 0;

        String excerpt = home.getItemText(position);
        assertEquals(30, excerpt.length());

    }

    @Test
    public void testItemStatisticsLink(){
        createAndLogNewUser(getUniqueId());
        ItemPageObject itemPageObject = home.toItemDetails();
        assertTrue(itemPageObject.isOnPage());


    }

    @Test
    public void testGetItemTypeCount(){
        createAndLogNewUser(getUniqueId());
        ItemPageObject itemPageObject = home.toItemDetails();

        itemPageObject.selectItemsByType(ItemController.Types.Car);
        int c = itemPageObject.getItemTypeCount();

        itemPageObject.selectItemsByType(ItemController.Types.Film);
        int f = itemPageObject.getItemTypeCount();

        itemPageObject.selectItemsByType(ItemController.Types.Music);
        int m = itemPageObject.getItemTypeCount();

        itemPageObject.selectItemsByType(ItemController.Types.Diverse);
        int d = itemPageObject.getItemTypeCount();

        itemPageObject.selectItemsByType(ItemController.Types.Sport);
        int s = itemPageObject.getItemTypeCount();
        itemPageObject.toHomePage();


        CreateItemPageObject cipo = home.toCreateItem();
        home = cipo.createItem("foo", "Car", "text");
        cipo = home.toCreateItem();
        home = cipo.createItem("foo", "Film", "text");
        cipo = home.toCreateItem();
        home = cipo.createItem("foo", "Music", "text");
        cipo = home.toCreateItem();
        home = cipo.createItem("foo", "Diverse", "text");
        cipo = home.toCreateItem();
        home = cipo.createItem("foo", "Sport", "text");



        itemPageObject = home.toItemDetails();

        itemPageObject.selectItemsByType(ItemController.Types.Car);
        itemPageObject.getItemTypeCount();

        assertEquals(c + 1, itemPageObject.getItemTypeCount());

        itemPageObject.selectItemsByType(ItemController.Types.Film);
        itemPageObject.getItemTypeCount();

        assertEquals(f + 1, itemPageObject.getItemTypeCount());

        itemPageObject.selectItemsByType(ItemController.Types.Music);
        itemPageObject.getItemTypeCount();

        assertEquals(m  + 1, itemPageObject.getItemTypeCount());

        itemPageObject.selectItemsByType(ItemController.Types.Sport);
        itemPageObject.getItemTypeCount();

        assertEquals(s + 1, itemPageObject.getItemTypeCount());

        itemPageObject.selectItemsByType(ItemController.Types.Diverse);
        itemPageObject.getItemTypeCount();

        assertEquals(d  + 1, itemPageObject.getItemTypeCount());
    }




   @Test
    public void testItemCollectionFilter(){
       createAndLogNewUser(getUniqueId());
       ItemPageObject itemPageObject = home.toItemDetails();
       itemPageObject.selectItemsByCollection(ItemController.Collection.Indoor);
       int i = itemPageObject.getNumberOfDisplayedItems();
       itemPageObject.selectItemsByCollection(ItemController.Collection.Outdoor);
       int o = itemPageObject.getNumberOfDisplayedItems();
       home = itemPageObject.toHomePage();

       CreateItemPageObject createItemPageObject = home.toCreateItem();
       home = createItemPageObject.createItem("foo", "Film", "title");
       createItemPageObject = home.toCreateItem();
       home = createItemPageObject.createItem("foo", "Sport", "text");

       itemPageObject = home.toItemDetails();
       itemPageObject.selectItemsByCollection(ItemController.Collection.Indoor);
       assertEquals(i + 1, itemPageObject.getNumberOfDisplayedItems());

       itemPageObject.selectItemsByCollection(ItemController.Collection.Outdoor);
       assertEquals(o + 1, itemPageObject.getNumberOfDisplayedItems());
   }

    @Test
    public void testUseItem(){
        String itemName = getUniqueTitle();
        String first = getUniqueId();
        createAndLogNewUser(first);
        CreateItemPageObject create = home.toCreateItem();
        home = create.createItem(itemName,"Car","title");

        home.setUsage(itemName, false);
        assertFalse(home.isUsing(itemName));
        assertEquals(0, home.getNumberOfUsages(itemName));

        home.setUsage(itemName, true);
        assertTrue(home.isUsing(itemName));
        assertEquals(1, home.getNumberOfUsages(itemName));

        home.setUsage(itemName, false);
        assertFalse(home.isUsing(itemName));
        assertEquals(0, home.getNumberOfUsages(itemName));
    }

}
