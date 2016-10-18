package org.maj.sm;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.maj.sm.dao.AccountServiceDAO;
import org.maj.sm.dao.AccountServiceDaoGAEDS;
import org.maj.sm.model.Account;
import org.maj.sm.model.BusinessUnit;
import org.maj.sm.model.Campaign;
import org.maj.sm.model.MSAAccount;
import org.maj.sm.model.Marketplace;
import org.maj.sm.model.Product;
import org.maj.sm.service.AccountService;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;

import static org.junit.Assert.*;

public class AccountServiceTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    protected Closeable session;
    
    private AccountService accountService;

    @BeforeClass
    public static void setUpBeforeClass() {
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
        AccountServiceDAO dao = new AccountServiceDaoGAEDS();
        this.accountService = new AccountService();
        this.accountService.setAccountServiceDAO(dao);        
    }

    @After
    public void tearDown() {
        AsyncCacheFilter.complete();
        this.session.close();
        this.helper.tearDown();
    }
    
    @Test
    public void testService(){
    	MSAAccount msaAccount = this.accountService.createAccount("Root", "Root Account");
    	BusinessUnit usaUnit = this.accountService.createBusinessUnit("usUnit", "USA buiness unit", msaAccount);
    	BusinessUnit europeUnit = this.accountService.createBusinessUnit("europeUnit", "Europe buiness unit", msaAccount);
    	
    	BusinessUnit flUnit = this.accountService.createBusinessUnit("flUnit", "Florida buiness unit", usaUnit);
    	
    	//Test 1 - make sure it was saved
    	Assert.assertNotNull(msaAccount.getId());
    	
    	//Test 2 - make sure the whole chain is there
    	
    	Assert.assertEquals(2, msaAccount.getChildAccounts().size());
    	Assert.assertEquals(1, usaUnit.getChildAccounts().size());
    	
    }
    
    

}
