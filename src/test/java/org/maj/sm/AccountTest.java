package org.maj.sm;

import java.util.Date;

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
import org.maj.sm.model.Account;
import org.maj.sm.model.Campaign;

import static com.googlecode.objectify.ObjectifyService.ofy;


public class AccountTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	protected Closeable session;
	
	@BeforeClass
    public static void setUpBeforeClass() {
        // Reset the Factory so that all translators work properly.
        ObjectifyService.setFactory(new ObjectifyFactory());
        ObjectifyService.register(Account.class);
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
    public void testAccount(){
    	Account parent = new Account();
    	parent.setName("Parent");
    	ofy().save().entity(parent).now();
    	
    	Account child = new Account();
    	child.setName("Child");
    	child.addParentAccount(new Date(), parent.getId());    	
    	ofy().save().entity(child).now();
    	
    	parent.addChildAccount(new Date(), child.getId());
    	ofy().save().entity(parent).now();
    	
    	Assert.assertNotNull(parent);
    	Assert.assertNotNull(parent);
    	
    	
    	//retrieve the parent and test
    	
    	Account parentRetrieved = ofy().load().type(Account.class).filterKey(Key.create(Account.class, parent.getId())).first().now();
    	
    	Assert.assertEquals(parent.getChildAccount(), parentRetrieved.getChildAccount());
    	
    }
    
    
}
