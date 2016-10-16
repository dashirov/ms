package org.maj.sm.model;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import org.maj.sm.utility.ChangeLogEntryComparator;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Campaign {

	@Id public String code;
	public String description;
	SortedSet<ChangeLogEntry<Double>> priceChangeLog = new TreeSet<>(new ChangeLogEntryComparator());
	
	public Campaign() {
		super();
	}

	public Campaign(String code, String description) {
		super();
		this.code = code;
		this.description = description;
	}
	
	public void addPrice(Date date, Double price){
		this.priceChangeLog.add(new ChangeLogEntry(date,price)
		);
	}

	@Override
	public String toString() {
		return "Campaign [code=" + code + ", description=" + description + ", price=" + this.priceChangeLog.last().getValue() + "]";
	}
	
}
