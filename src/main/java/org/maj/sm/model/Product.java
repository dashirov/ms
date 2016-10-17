package org.maj.sm.model;

import com.googlecode.objectify.annotation.Container;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Stringify;
import org.maj.sm.model.enums.CampaignStatus;
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

    @Container
	ChangeLog<ProductStatus> statusChangeLog = new ChangeLog<ProductStatus>();

	public Product() {
		super();
	}

	public Product(String code, String name, String description) {
		super();
		this.code = code;
        this.name = name;
		this.description = description;
	}

	public void setStatus(final Date effectiveDate, ProductStatus status){
        ChangeLogEntry<ProductStatus>  entry= new ChangeLogEntry<ProductStatus>(effectiveDate,status);
        this.statusChangeLog.addLogEntry(entry);
    }

    public ProductStatus getStatus(final Date date){
        return this.statusChangeLog.getValue(date,ProductStatus.DELETED);
    }

	@Override
	public String toString() {
		return "Product [code=" + code + ", name=" + name+ ", description=" + description + ", status=" + this.getStatus(new Date()).toString() + "]";
	}
	
}
