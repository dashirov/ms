package org.maj.sm.dao;

import org.maj.sm.model.BusinessUnit;
import org.maj.sm.model.MSAAccount;

public interface AccountServiceDAO {
	
	MSAAccount saveMSAccount(MSAAccount msaAccount);
	BusinessUnit createBU(BusinessUnit bu, MSAAccount parent);
	BusinessUnit createBU(BusinessUnit bu, BusinessUnit parent);
}
