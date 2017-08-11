import org.junit.Test;
import po.CreateUserPageObject;
import po.HomePageObject;
import po.LoginPageObject;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Stella on 11.08.2017.
 */
public class UserCreateAndLoginIT extends WebTestBase {
    @Test
    public void testHomePage() {
        home.toStartingPage();
        assertTrue(home.isOnPage());
    }


    @Test
    public void testLoginLink() {
        LoginPageObject login = home.toLogin();
        assertTrue(login.isOnPage());
    }

    @Test
    public void testLoginWrongUser() {
        LoginPageObject login = home.toLogin();
        HomePageObject home = login.clickLogin(getUniqueId(), "foo");
        assertNull(home);
        assertTrue(login.isOnPage());
    }

    @Test
    public void testLogin() {
        String userId = getUniqueId();
        createAndLogNewUser(userId, "foo", "bar");
        home.logout();

        assertFalse(home.isLoggedIn());
        LoginPageObject login = home.toLogin();
        home = login.clickLogin(userId, "foo");

        assertNotNull(home);
        assertTrue(home.isOnPage());
        assertTrue(home.isLoggedIn(userId));
    }

    @Test
    public void testCreateValidUser() {
        LoginPageObject login = home.toLogin();
        CreateUserPageObject create = login.clickCreateNewUser();
        assertTrue(create.isOnPage());

        String userName = getUniqueId();

        HomePageObject home = create.createUser(userName, "foo", "foo", "a", "b", "c");
        assertNotNull(home);
        assertTrue(home.isOnPage());
        assertTrue(home.isLoggedIn(userName));

        home.logout();
        assertFalse(home.isLoggedIn());
    }

    @Test
    public void testCreateUserFailDueToPasswordMismatch() {
        LoginPageObject login = home.toLogin();
        CreateUserPageObject create = login.clickCreateNewUser();

        HomePageObject home = create.createUser(getUniqueId(), "foo", "bar", "a", "b", "c");
        assertNull(home);
        assertTrue(create.isOnPage());
    }
}
