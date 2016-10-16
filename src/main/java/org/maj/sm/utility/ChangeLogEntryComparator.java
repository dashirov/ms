package org.maj.sm.utility;

import java.util.Comparator;

import org.maj.sm.model.ChangeLogEntry;

public class ChangeLogEntryComparator implements Comparator<ChangeLogEntry>{

	@Override
	public int compare(ChangeLogEntry o1, ChangeLogEntry o2) {
		return o2.getEffectiveDate().compareTo(o1.getEffectiveDate());
	}

}
