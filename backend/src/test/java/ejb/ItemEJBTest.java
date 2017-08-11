package ejb;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Stella on 11.08.2017.
 */
@RunWith(Arquillian.class)
public class ItemEJBTest extends EJBTestBase {

    @Test
    public void testCreateItem(){
        String userId = "foo";
        String itemType = "Car";
        assertTrue(createUser(userId));


        assertEquals(0, itemEJB.getAllItems().size());

        long id = itemEJB.createItem(userId, itemType, "Honda", "Civic");
        assertEquals(id, (long)itemEJB.getItem(id).getId());
        assertEquals(1, itemEJB.getAllItems().size());
        assertEquals(1, itemEJB.getAllItemsByType(itemType).size());

    }

    @Test(expected = javax.ejb.EJBException.class)
    public void testCreateItemWrongUserId(){
        itemEJB.createItem("foo", "Car", "Honda", "Civic");

    }

    @Test(expected = javax.ejb.EJBException.class)
    public void testCreateItemWrongItemType(){
        String userId = "foo";
        createUser(userId);
        itemEJB.createItem(userId, "foo", "Honda", "Civic");

    }

    @Test
    public void testGetAllItemsByType(){
        String userId = "foo";
        String itemType1 = "Car";
        String itemType2 = "Diverse";

        assertTrue(createUser(userId));

        itemEJB.createItem(userId, itemType1, "Honda", "Civic");
        itemEJB.createItem(userId, itemType1, "Nissan", "Leaf");
        itemEJB.createItem(userId, itemType2, "foo", "bar");

        assertEquals(3, itemEJB.getAllItems().size());
        assertEquals(2, itemEJB.getAllItemsByType(itemType1).size());
        assertEquals(itemType1, itemEJB.getAllItems().get(0).getType());

    }

    @Test
    public void testGetAllItemsByUser(){
        String userId1 = "foo";
        String userId2 = "bar";
        createUser(userId1);
        createUser(userId2);

        itemEJB.createItem(userId1, "Car", "Honda", "Civic");
        itemEJB.createItem(userId2, "Car", "Nissan", "Leaf");

        assertEquals(2, itemEJB.getAllItems().size());
        assertEquals(1, itemEJB.getAllItemsByUser(userId1).size());
        assertEquals(1, itemEJB.getAllItemsByUser(userId2).size());

        assertEquals(userId1,itemEJB.getAllItemsByUser(userId1).get(0).getUser().getUserId());
        assertEquals("Honda",itemEJB.getAllItemsByUser(userId1).get(0).getTitle());
        assertEquals("Civic",itemEJB.getAllItemsByUser(userId1).get(0).getText());




    }
}
