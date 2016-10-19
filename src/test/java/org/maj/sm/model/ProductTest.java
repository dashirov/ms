package org.maj.sm.model;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;
import org.junit.*;
import org.maj.sm.model.Campaign;
import org.maj.sm.model.Marketplace;
import org.maj.sm.model.Product;
import org.maj.sm.model.enums.ProductStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static org.junit.Assert.*;

/**
 * Created by dashirov on 10/17/16.
 */
public class ProductTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    protected Closeable session;

    @BeforeClass
    public static void setUpBeforeClass() {
        // Reset the Factory so that all translators work properly.
        ObjectifyService.setFactory(new ObjectifyFactory());
        ObjectifyService.register(Campaign.class);
        ObjectifyService.register(Product.class);
        ObjectifyService.register(Marketplace.class);
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
    public void testSetStatus() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Product productOne = new Product();
        productOne.setName("FirstAid Pro");
        productOne.setCode("FAPRO");
        productOne.setDescription("Cool description of FirstAid Pro product");
        productOne.setStatus(new Date(), ProductStatus.ACTIVE);
        Key productOneKey = ofy().save().entity(productOne).now();

        Product productOneRetrieved = (Product) ofy().load().key(productOneKey).now();
        Assert.assertEquals(productOne.getCode(),productOneRetrieved.getCode());

        productOneRetrieved.setStatus(new Date(), ProductStatus.ACTIVE);
        Assert.assertEquals(productOne.getStatus(new Date()),ProductStatus.ACTIVE);
        productOne.setStatus(new Date(), ProductStatus.RETIRED);
        Assert.assertEquals(productOne.getStatus(df.parse("2001-01-01")), ProductStatus.NONEXISTING);


    }

    @Test
    public void testToString() throws Exception {

    }

}