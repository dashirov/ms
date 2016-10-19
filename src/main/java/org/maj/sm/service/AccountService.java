package org.maj.sm.service;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.NotFoundException;
import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;
import org.maj.sm.dao.AccountServiceDAO;
import org.maj.sm.model.Account;
import org.maj.sm.model.BusinessUnit;
import org.maj.sm.model.MSAAccount;

import com.googlecode.objectify.Key;

/**
 * This is a higher level service layer
 * 
 * @author shamik.majumdar
 *
 */

public class AccountService {
	
	private AccountServiceDAO accountServiceDAO;
	
	public void setAccountServiceDAO(AccountServiceDAO accountServiceDAO) {
		this.accountServiceDAO = accountServiceDAO;
	}

	public MSAAccount createMSAAccount(String name, String description) {
		 MSAAccount myWalledGarden = new MSAAccount();
		 myWalledGarden.setName(name);
	     myWalledGarden.setDescription(description);
	     return (MSAAccount) accountServiceDAO.saveAccount(myWalledGarden);
	}

	public BusinessUnit createBusinessUnit(String name, String description,Account parent){
		BusinessUnit bu = new BusinessUnit();
		bu.setName(name);
		bu.setDescription(description);
		bu.setParentAccount(parent.getId());		
		return accountServiceDAO.createBusinessUnit(bu, parent);
	}


	public BusinessUnit moveBusinessUnit(BusinessUnit businessUnit, Account newParentAccount){
        return accountServiceDAO.moveBusinessUnit(businessUnit,newParentAccount);
    }


}
