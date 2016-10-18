package org.maj.sm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public void testAccount() throws ParseException{
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date oldDate = dateFormat.parse("2016-10-01");
    	Account parent = new Account();
    	parent.setName("World");

        ofy().save().entity(parent).now();

        Account world = ofy().load().entity(parent).now();

        Account europe = new Account();
		europe.setName("Europe");
        europe.addParentAccount(dateFormat.parse("0001-01-01"), parent.getId());
        ofy().save().entity(europe).now();
        world.addChildAccount(dateFormat.parse("0001-01-01"), europe.getId());



		Account france = new Account();
		france.setName("France");
        france.addParentAccount(dateFormat.parse("0987-01-01"), europe.getId());
        ofy().save().entity(france).now();
        europe.addChildAccount(dateFormat.parse("0987-01-01"),france.getId());


        // Claimed not before 1492
		Account northAmerica = new Account();
		northAmerica.setName("North America");
        northAmerica.addParentAccount(dateFormat.parse("1492-01-01"), world.getId());
        ofy().save().entity(northAmerica).now();
        world.addChildAccount(dateFormat.parse("1492-01-01"),northAmerica.getId());


        // Established July 4, 1776
		Account unitedStates = new Account();
		unitedStates.setName("United States");
        unitedStates.addParentAccount(dateFormat.parse("1776-07-04"), northAmerica.getId());
        ofy().save().entity(unitedStates).now();
        northAmerica.addChildAccount(dateFormat.parse("1776-07-04"), unitedStates.getId());



        // Established July 26, 1788
		Account newYorkState = new Account();
		newYorkState.setName("New York State");
        newYorkState.addParentAccount(dateFormat.parse("1788-07-26"), unitedStates.getId());
        ofy().save().entity(newYorkState).now();
        unitedStates.addChildAccount(dateFormat.parse("1788-07-26"), newYorkState.getId());



		// Established  as a fort in  April 17, 1524 and belonged to France
		Account newYorkCity = new Account();
		newYorkCity.setName("City of New York");
		newYorkCity.addParentAccount(dateFormat.parse("1524-04-17"), france.getId());
        france.addChildAccount(dateFormat.parse("1524-04-17"), newYorkCity.getId());
        newYorkCity.addParentAccount(dateFormat.parse("1788-07-26"), newYorkState.getId());
        ofy().save().entity(newYorkCity).now();
        newYorkState.addChildAccount(dateFormat.parse("1788-07-26"), newYorkCity.getId());



        ofy().save().entities(world,europe,northAmerica,france,northAmerica,newYorkCity,newYorkState).now();


    	Account child = new Account();
    	child.setName("Child");
    	child.addParentAccount(oldDate, parent.getId());    	
    	ofy().save().entity(child).now();
    	
    	parent.addChildAccount(oldDate, child.getId());
    	ofy().save().entity(parent).now();
    	
    	Assert.assertNotNull(parent);
    	Assert.assertNotNull(parent);
    	
    	
    	//retrieve the parent and test
    	//parent --> child 
    	Account parentRetrieved = ofy().load().type(Account.class).filterKey(Key.create(Account.class, parent.getId())).first().now();
    	
    	Assert.assertEquals(parent.getChildAccount(), parentRetrieved.getChildAccount());
    	
    	//Now add a middle child
    	//parent --> middle --> child
    	Date d = dateFormat.parse("2016-10-10");
    	Account middle = new Account();
    	middle.setName("Middle");
    	middle.addParentAccount(d, parent.getId());
    	middle.addChildAccount(d,child.getId());
    	ofy().save().entity(middle).now();
    	
    	parent.addChildAccount(d, middle.getId());
    	ofy().save().entity(parent).now();
    	
    	child.addParentAccount(d, middle.getId());
    	
    	parentRetrieved = ofy().load().type(Account.class).filterKey(Key.create(Account.class, parent.getId())).first().now();
    	Assert.assertEquals(middle.getId(), Long.valueOf(parentRetrieved.getChildAccount()));
    }
    
    
}
