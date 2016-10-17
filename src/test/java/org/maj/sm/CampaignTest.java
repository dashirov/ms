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

import static com.googlecode.objectify.ObjectifyService.ofy;


public class CampaignTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	protected Closeable session;
	
	@BeforeClass
    public static void setUpBeforeClass() {
        // Reset the Factory so that all translators work properly.
        ObjectifyService.setFactory(new ObjectifyFactory());
        ObjectifyService.register(Campaign.class);
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
        Campaign campaign = new Campaign("XX","Test Code");

        // 1) Save Campaign to data store
        ofy().save().entity(campaign).now();

        // 2) retrieve campaign from data store
        Campaign campaignRetrieved = ofy().load().type(Campaign.class).filterKey(Key.create(Campaign.class, campaign.code)).first().now();
        Assert.assertNotNull(campaignRetrieved);
        Assert.assertEquals(campaign.code, campaignRetrieved.code);
        Assert.assertEquals(campaign.description, campaignRetrieved.description);
    }
}
