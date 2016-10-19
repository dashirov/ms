package org.maj.sm.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.Key;
import org.maj.sm.model.Account;
import org.maj.sm.model.BusinessUnit;
import org.maj.sm.model.MSAAccount;

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
}
