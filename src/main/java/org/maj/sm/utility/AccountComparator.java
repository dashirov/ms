package org.maj.sm.utility;

import org.maj.sm.model.Account;
import org.maj.sm.model.ChangeLogEntry;

import java.util.Comparator;

public class AccountComparator implements Comparator<Account>{

	@Override
	public int compare(Account o1, Account o2) {
		return o2.getId().compareTo(o1.getId());
	}

}
