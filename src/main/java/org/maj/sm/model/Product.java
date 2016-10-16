package org.maj.sm.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Stringify;
import org.maj.sm.model.enums.ProductStatus;
import org.maj.sm.utility.ChangeLogEntryComparator;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
public class Product {

	@Id public String code;
	public String name;
	public String description;
	SortedSet<ChangeLogEntry<ProductStatus>> statusChangeLog = new TreeSet<>(new ChangeLogEntryComparator());

	public Product() {
		super();
	}

	public Product(String code, String name, String description) {
		super();
		this.code = code;
        this.name = name;
		this.description = description;
	}
	
	public void addPrice(Date date, Double price){
		this.statusChangeLog.add(new ChangeLogEntry(date,price)
		);
	}

	@Override
	public String toString() {
		return "Product [code=" + code + ", name=" + name+ ", description=" + description + ", price=" + this.statusChangeLog.last().getValue() + "]";
	}
	
}
