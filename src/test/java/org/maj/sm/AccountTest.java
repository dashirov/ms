package org.maj.sm;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;
import org.junit.*;
import org.maj.sm.model.*;
import org.maj.sm.model.enums.ProductStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedSet;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by dashirov on 10/17/16.
 */
public class AccountTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    protected Closeable session;

    @BeforeClass
    public static void setUpBeforeClass() {
        // Reset the Factory so that all translators work properly.
        ObjectifyService.setFactory(new ObjectifyFactory());
        ObjectifyService.register(Campaign.class);
        ObjectifyService.register(Product.class);
        ObjectifyService.register(Marketplace.class);
        ObjectifyService.register(MSAAccount.class);
        ObjectifyService.register(Account.class);
        ObjectifyService.register(BusinessUnit.class);
    }

    @Before
    public void setUp() {
        this.session = ObjectifyService.begin();
        this.helper.setUp();
    }

    @After
    public void tearDown() {
        AsyncCacheFilter.complete();
        this.session.close();
        this.helper.tearDown();
    }

    @Test
    public void testSubjugateAccount() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date effectiveDate=df.parse("2001-01-01");
        Date effectiveDate2=df.parse("2002-01-01");
        Date effectiveDate3=df.parse("2003-01-01");
        MSAAccount myWalledGarden = new MSAAccount();
        myWalledGarden.setName("My MSA Account");
        myWalledGarden.setDescription("No description");
        Key<MSAAccount> rootKey = ofy().save().entity(myWalledGarden).now();

        BusinessUnit usOperations = new BusinessUnit();
        usOperations.setName("US Operations");
        usOperations.setDescription("My US Operations Business Unit");
        Key<BusinessUnit> usOperationsKey = ofy().save().entity(usOperations).now();


        ofy().save().entities(myWalledGarden,usOperations).now();

        BusinessUnit nyOperations = new BusinessUnit();
        nyOperations.setName("NY Operations");
        nyOperations.setDescription("My New York Operations Business Unit");
        Key<BusinessUnit> nyOperationsKey = ofy().save().entity(nyOperations).now();

        BusinessUnit maOperations = new BusinessUnit();
        nyOperations.setName("MA Operations");
        nyOperations.setDescription("My Massachusetts Operations Business Unit");
        Key<BusinessUnit> maOperationsKey = ofy().save().entity(maOperations).now();

        ofy().save().entities(myWalledGarden,usOperations,nyOperations,maOperations).now();
        ofy().save().entities(myWalledGarden,usOperations,nyOperations,maOperations).now();






    }


}