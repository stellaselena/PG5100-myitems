package ejb;

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
                .addPackages(true, "ejb", "entity")
                .addPackages(true, "org.apache.commons.codec")
                .addClass(DeleterEJB.class)
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    protected UserEJB userEJB;

    @EJB
    private DeleterEJB deleterEJB;

    @Before
    @After
    public void emptyDatabase() {

        deleterEJB.deleteEntities(User.class);
    }
}
