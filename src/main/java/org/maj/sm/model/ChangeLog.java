package org.maj.sm.model;

import java.util.Date;

public class ChangeLog<T> implements Comparable<ChangeLog<T>>{
	
	public T value;
	
	public Date effectiveDate;
	
	public ChangeLog() {
		super();
	}

	public ChangeLog(T value, Date effectiveDate) {
		super();
		this.value = value;
		this.effectiveDate = effectiveDate;
	}

	@Override
	public int compareTo(ChangeLog<T> other) {
		return this.effectiveDate.compareTo(other.effectiveDate);
	}
	

}
