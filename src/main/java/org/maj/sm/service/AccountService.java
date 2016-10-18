package org.maj.sm.service;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.maj.sm.dao.AccountServiceDAO;
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

	public MSAAccount createAccount(String name, String description) {
		MSAAccount myWalledGarden = new MSAAccount();
		 myWalledGarden.setName(name);
	     myWalledGarden.setDescription(description);
	     return accountServiceDAO.saveMSAccount(myWalledGarden);
	}
	
	public BusinessUnit createBusinessUnit(String name, String description,MSAAccount parent){
		BusinessUnit bu = new BusinessUnit();
		bu.setName(name);
		bu.setDescription(description);
		bu.setParentAccount(parent.getId());		
		return accountServiceDAO.createBU(bu, parent);

	}
	
	public BusinessUnit createBusinessUnit(String name, String description,BusinessUnit parent){
		BusinessUnit bu = new BusinessUnit();
		bu.setName(name);
		bu.setDescription(description);
		bu.setParentAccount(parent.getId());		
		return accountServiceDAO.createBU(bu, parent);

	}

}
