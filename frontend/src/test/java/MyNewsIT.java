import org.junit.Test;
import po.CreateItemPageObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Stella on 11.08.2017.
 */
public class MyNewsIT extends WebTestBase {

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
        String userId2 = getUniqueId();

        createAndLogNewUser(userId1);

        assertEquals(0, home.getNumberOfDisplayedItemsByUser(userId1));

        String title1 = getUniqueTitle();
        CreateItemPageObject cipo = home.toCreateItem();
        home = cipo.createItem(title1, "Car", "Honda Civic");
        assertEquals(1, home.getNumberOfDisplayedItemsByUser(userId1));

        home.logout();
        loginExistingUser(userId1);
        assertEquals(1, home.getNumberOfDisplayedItemsByUser(userId1));

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
}
