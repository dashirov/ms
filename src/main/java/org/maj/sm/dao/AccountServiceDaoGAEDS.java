package org.maj.sm.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.maj.sm.model.BusinessUnit;
import org.maj.sm.model.MSAAccount;

public class AccountServiceDaoGAEDS implements AccountServiceDAO{

	@Override
	public MSAAccount saveMSAccount(MSAAccount msaAccount) {		
		ofy().save().entity(msaAccount).now();		
		return msaAccount;
	}

	@Override
	public BusinessUnit createBU(BusinessUnit bu, MSAAccount parent) {
		ofy().save().entities(bu).now();
		parent.addChildAccount(bu.getId());
		ofy().save().entities(parent).now();
		return bu;
	}

	@Override
	public BusinessUnit createBU(BusinessUnit bu, BusinessUnit parent) {
		ofy().save().entities(bu).now();
		parent.addChildAccount(bu.getId());
		ofy().save().entities(parent).now();
		return bu;
	}
	
	

}
