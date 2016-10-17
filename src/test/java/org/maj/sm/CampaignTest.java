package org.maj.sm;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;

import org.junit.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.maj.sm.model.Campaign;
import org.maj.sm.model.Marketplace;
import org.maj.sm.model.Product;
import org.maj.sm.model.enums.CampaignStatus;
import org.maj.sm.model.enums.ProductStatus;
import sun.jvm.hotspot.oops.Mark;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.googlecode.objectify.ObjectifyService.ofy;


public class CampaignTest {

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
    public void doTest() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = new Date();
        Date dt2 = new Date();
        Date dt3 = new Date();
        try {
            dt1 = dateFormat.parse("2001-01-01");
            dt2 = dateFormat.parse("2001-01-02");
            dt3 = dateFormat.parse("1999-03-01");
        } catch (ParseException e){
            e.printStackTrace();
        }
        Marketplace google = new Marketplace();
        google.name="Google AdWords Market";
        google.id=ofy().save().entity(google).now().getId();

        Assert.assertEquals("Google AdWords Market", google.name);
        Assert.assertNotNull(google.id);
        Assert.assertNotEquals(google.id,Long.valueOf(0x0) );

        System.out.println(google.toString());

        Marketplace appnexus= new Marketplace();
        appnexus.name="AppNexus DSP Programatic Market";
        appnexus.id=ofy().save().entity(appnexus).now().getId();


        Marketplace appnexus2=ofy().load().entity(appnexus).safe();
        Assert.assertEquals(appnexus,appnexus2);

        Marketplace appnexus3=ofy().load().key(Key.create(Marketplace.class,appnexus.id)).now();
        Assert.assertEquals(appnexus2,appnexus2);
        Product cannedCatfood = new Product();
        cannedCatfood.name="Canned Catfood 16oz. metal can";
        cannedCatfood.code="CCF16";
        cannedCatfood.description="Some mandane copy describing boring canned cat food";
        cannedCatfood.setStatus(dt3,ProductStatus.NEW);
        cannedCatfood.setStatus(dt2, ProductStatus.ACTIVE);
        String productId = ofy().save().entity(cannedCatfood).now().getString();

        Product cannedCatfood2 = ofy().load().key( Key.create(Product.class,"CCF16") ).now();

        System.out.println("Canned catfood: " + productId);

        System.out.println("Canned catfood2: " + cannedCatfood2.description);

        Assert.assertEquals(cannedCatfood,cannedCatfood2);



        Campaign campaign = new Campaign("XX","Test Code");
        campaign.marketplace = google.id;

        campaign.product=cannedCatfood.code;


        campaign.setPrice(dt1, 15.0009);
        System.out.println(campaign.toString());
        Assert.assertEquals("Expected DELETED Campaign to show $0.00 Price", 0x0, campaign.getPrice(),0);

        campaign.setStatus(dt1, CampaignStatus.ACTIVE );
        System.out.println(campaign.toString());
        Assert.assertEquals("Expected ACTIVE Campaign to show $15.0009 Price", 15.0009, campaign.getPrice(),0.000001);

        campaign.setPrice(dt2, 10.0001);
        System.out.println(campaign.toString());
        Assert.assertEquals("Expected ACTIVE Campaign to show $10.0001 Price", 10.0001, campaign.getPrice(),0.000001);

        campaign.setStatus(dt2, CampaignStatus.PAUSED );
        System.out.println(campaign.toString());
        Assert.assertEquals("Expected PAUSED Campaign to show $0.00 Price", 0x0, campaign.getPrice(),0);
        Assert.assertEquals("Expected ACTIVE on " + dateFormat.format(dt1) +" Campaign to show $15.0009 Price", 15.0009, campaign.getPrice(dt1),0.000001);
        Assert.assertEquals("Expected DELETED on " + dateFormat.format(dt3) + "Campaign to show $0.00 Price", 0x0 , campaign.getPrice(dt3),0);

        System.out.println(campaign.toString());
        // 1) Save Campaign to data store
        ofy().save().entity(campaign).now();

        // 2) retrieve campaign from data store
        Campaign campaignRetrieved = ofy().load().type(Campaign.class).filterKey(Key.create(Campaign.class, campaign.code)).first().now();
        Assert.assertNotNull(campaignRetrieved);
        Assert.assertEquals(campaign.code, campaignRetrieved.code);
        Assert.assertEquals(campaign.description, campaignRetrieved.description);
    }
}
