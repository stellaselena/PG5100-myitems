package ejb;

import entity.Item;
import entity.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import util.DeleterEJB;

import javax.ejb.EJB;

/**
 * Created by Stella on 11.08.2017.
 */
public abstract class EJBTestBase {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "ejb", "entity", "validation")
                .addPackages(true, "org.apache.commons.codec")
                .addClass(DeleterEJB.class)
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    protected UserEJB userEJB;

    @EJB
    protected ItemEJB itemEJB;

    @EJB
    private DeleterEJB deleterEJB;

    @Before
    @After
    public void emptyDatabase() {

        deleterEJB.deleteEntities(Item.class);

        deleterEJB.deleteEntities(User.class);
    }

    protected boolean createUser(String user){
        return userEJB.createUser(user,"foo","a","b","c");
    }

    protected boolean createUser(String user, String password){
        return userEJB.createUser(user,password,"a","b","c");
    }
}
