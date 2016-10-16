package org.maj.sm.utility;

import java.util.Comparator;

import org.maj.sm.model.PriceChangeLog;

public class PriceComparator implements Comparator<PriceChangeLog>{

	@Override
	public int compare(PriceChangeLog o1, PriceChangeLog o2) {
		return o2.getPriceChangeDate().compareTo(o1.getPriceChangeDate());
	}

}
