package org.maj.sm.service;


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
import org.maj.sm.model.enums.AccountType;
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
    	MSAAccount msaAccount = this.accountService.createMSAAccount("ACME Company", "ACME Company Master Service Agreemeent Account");
        Assert.assertNotNull("Expecting the backend to populate object identifier",msaAccount.getId());
        Assert.assertEquals("Expecting the POJO to set correct account type", AccountType.MSA, msaAccount.getType());
        Assert.assertEquals("Expecting account to have no subaccounts as none were created yet",0,msaAccount.getChildAccounts().size());

    	BusinessUnit usaUnit = this.accountService.createBusinessUnit("US Operations", "USA buiness unit", msaAccount);
        Assert.assertEquals("Expecting msa account to have one subaccount",1,msaAccount.getChildAccounts().size());
        Assert.assertEquals("Expecting first global business unit to point to msa as a parent",msaAccount.getId(),usaUnit.getParentAccount());

    	BusinessUnit europeUnit = this.accountService.createBusinessUnit("EU Operations", "Europe buiness unit", msaAccount);
        Assert.assertEquals("MSA Account should list two distinct subaccounts",2,msaAccount.getChildAccounts().size());

    	BusinessUnit flUnit = this.accountService.createBusinessUnit("flUnit", "Florida buiness unit", usaUnit);
        BusinessUnit nyUnit = this.accountService.createBusinessUnit("nyUnit", "NY business unit",msaAccount);
        Assert.assertEquals("MSA Account should have 3 subaccounts",3,msaAccount.getChildAccounts().size());
        // Move an account
    	nyUnit = this.accountService.moveBusinessUnit(nyUnit,usaUnit);
        Assert.assertEquals("MSA Account should have 2 subaccounts",2,msaAccount.getChildAccounts().size());
        Assert.assertEquals("US Account should have 2 subaccounts",2,usaUnit.getChildAccounts().size());
        Assert.assertTrue(usaUnit.getChildAccounts().contains(flUnit.getId()));
        Assert.assertTrue(usaUnit.getChildAccounts().contains(nyUnit.getId()));
    	//Test 1 - make sure it was saved
    	Assert.assertNotNull(msaAccount.getId());

        Product cannedCatfood = accountService.createProduct(msaAccount,"CCF16z","Canned Catfood 16oz","Cool description");
        Assert.assertEquals("CCF16Z",cannedCatfood.getCode());
        Assert.assertTrue(msaAccount.getProducts().contains(cannedCatfood.getCode()));

        cannedCatfood = accountService.moveProduct(cannedCatfood,usaUnit);
        Assert.assertEquals(usaUnit.getId(),cannedCatfood.getParentAccount());
        Assert.assertFalse(msaAccount.getProducts().contains(cannedCatfood.getCode()));
        Assert.assertTrue(usaUnit.getProducts().contains(cannedCatfood.getCode()));
        Assert.assertEquals(usaUnit.getId(),cannedCatfood.getParentAccount());
    }
    


}
