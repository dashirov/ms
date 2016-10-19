package org.maj.sm.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.Key;
import org.maj.sm.model.*;
import sun.jvm.hotspot.utilities.Assert;

public class AccountServiceDaoGAEDS implements AccountServiceDAO{


	@Override
	public Account saveAccount(Account account) {
		ofy().save().entity(account).now();
		return account;
	}

	@Override
	public BusinessUnit createBusinessUnit(BusinessUnit businessUnit, Account parent) {
        /** if not yet saved (id not set) save it.
          * TODO: Under expected conditions getId should always be null
          * should we assert:
          *   businessUnit.getId is null
          *   businessUnit.getParentAccount is null
          *   parent.getChildAccounts().contains(businessUnit.getId())
          */
        ofy().save().entities(businessUnit).now();
        parent.addChildAccount(businessUnit.getId());
        businessUnit.setParentAccount(parent.getId());
        ofy().save().entities(parent,businessUnit).now();
		return businessUnit;
	}

    @Override
    public BusinessUnit moveBusinessUnit(BusinessUnit businessUnit, Account parent) {
        Long oldParentId = businessUnit.getParentAccount();
        Account oldParent = ofy().load().type(Account.class).id(oldParentId).now();
        oldParent.getChildAccounts().remove(businessUnit.getId());
        parent.addChildAccount(businessUnit.getId());
        businessUnit.setParentAccount(parent.getId());
        ofy().save().entities(parent,oldParent,businessUnit).now();
        return businessUnit;
    }

    @Override
    public Product saveProduct(Product product) {
        ofy().save().entity(product).now();
        return product;
    }

    @Override
    public Product createProduct(Product product, Account parent) {
        Assert.that(product.getCode()!=null,"Product code is required");
        ofy().save().entity(product).now();
        parent.addProduct(product.getCode());
        product.setParentAccount(parent.getId());
        ofy().save().entities(parent,product).now();
        return product;
    }

    @Override
    public Product moveProduct(Product product, Account parent) {
        Long oldParentId = product.getParentAccount();
        Account oldParent = ofy().load().type(Account.class).id(oldParentId).now();
        oldParent.removeProduct(product.getCode());
        parent.addProduct(product.getCode());
        product.setParentAccount(parent.getId());
        ofy().save().entities(parent,oldParent,product).now();
        return product;
    }

    @Override
    public Marketplace createMarketplace(Marketplace marketplace, MSAAccount parentAccount) {
        Assert.that(parentAccount.getId()!=null,"Parent MSA Account must have an ID field");
        parentAccount=ofy().load().entity(parentAccount).now();
        ofy().save().entity(marketplace).now();
        parentAccount.addMarketplace(marketplace);
        ofy().save().entities(parentAccount).now();
        return marketplace;
    }

    @Override
    public Marketplace saveMarketplace(Marketplace marketplace) {
        Key markerplaceKey=ofy().save().entity(marketplace).now();
        return marketplace;
    }
}
