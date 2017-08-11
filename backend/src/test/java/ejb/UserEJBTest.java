package ejb;

import entity.User;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJBException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Stella on 11.08.2017.
 */

@RunWith(Arquillian.class)
public class UserEJBTest extends EJBTestBase {

    private boolean createUser(String user, String password) {
        return userEJB.createUser(user, password, "a", "b", "c");
    }

    @Test
    public void testCanCreateAUser() {

        String user = "foo";
        String password = "bar";

        assertTrue(createUser(user, password));
    }

    @Test(expected = EJBException.class)
    public void testCreateUserWithWrongId() {

        String user = "!!!";
        String password = "bar";

        createUser(user, password);
    }

    @Test(expected = EJBException.class)
    public void testCreateAUserWithEmptyId() {

        String user = "    ";
        String password = "bar";

        createUser(user, password);
    }

    @Test
    public void testNoTwoUsersWithSameId() {

        String user = "foo";

        assertTrue(createUser(user, "a"));


        assertFalse(createUser(user, "b"));
    }

    @Test
    public void testSamePasswordLeadSToDifferentHashAndSalt() {

        String password = "password";
        String user1 = "foo";
        String user2 = "bar";

        createUser(user1, password);
        createUser(user2, password);

        User u1 = userEJB.getUser(user1);
        User u2 = userEJB.getUser(user2);


        assertNotEquals(u1.getHash(), u2.getHash());
        assertNotEquals(u1.getSalt(), u2.getSalt());
    }

    @Test
    public void testVerifyPassword() {

        String user = "foo";
        String p1 = "bar";
        String p2 = "wrong";

        createUser(user, p1);

        assertTrue(userEJB.login(user, p1));
        assertFalse(userEJB.login(user, p2));
    }

    @Test
    public void testBeSurePasswordIsNotStoredInPlain() {

        String user = "foo";
        String password = "bar";
        createUser(user, password);

        User entity = userEJB.getUser(user);
        assertNotEquals(password, entity.getUserId());
        assertNotEquals(password, entity.getHash());
        assertNotEquals(password, entity.getSalt());
    }
}
