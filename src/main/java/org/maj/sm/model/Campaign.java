package org.maj.sm.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;


import com.googlecode.objectify.annotation.Container;
import org.maj.sm.model.enums.CampaignStatus;
import org.maj.sm.utility.ChangeLogEntryComparator;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Campaign {

	@Id public String code;
	public String description;
    public Long marketplace;  // where do we buy the traffc from
    public String product; // what product are we advertising

    @Container
    private ChangeLog<Double> priceChangeLog = new ChangeLog<>();

    @Container
    private ChangeLog<CampaignStatus> statusChangeLog=new ChangeLog<>();
	
	public Campaign() {
		super();
	}

	public Campaign(String code, String description) {
		super();
		this.code = code;
		this.description = description;
	}
	public void setPrice(Date effectiveDate, Double campaignPrice){
		this.priceChangeLog.addLogEntry(new ChangeLogEntry<>(effectiveDate,campaignPrice));
	}

	public void setStatus(Date effectiveDate, CampaignStatus campaignStatus){
        this.statusChangeLog.addLogEntry(new ChangeLogEntry<>(effectiveDate,campaignStatus));
    }

    public CampaignStatus getStatus(){
        return getStatus(new Date());
    }

    public CampaignStatus getStatus(Date date) {
        return this.statusChangeLog.getValue(date,CampaignStatus.NOT_FOUND);
    }

    public Double getPrice(){
        return getPrice(new Date());
    }

    public Double getPrice(Date date){
        /**
         * Price is more than zero if campaign is active on the same day
         */
        if (!getStatus(date).equals(CampaignStatus.ACTIVE)){
            return 0.0;
        }
        /**
         * Now that we know it was active, find the price or default to $0.00
         */
        return priceChangeLog.getValue(date,0.00);
    }

    
    @Override
    public String toString() {
        return "Campaign{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", marketplace=" + marketplace +
                ", product='" + product + '\'' +

                ", price=" + this.getPrice(new Date()).toString() +
                ", priceChangeLog=" + this.priceChangeLog.toString()+

                ", status=" + this.getStatus(new Date()).toString() +
                ", statusChangeLog=" + this.statusChangeLog.toString() +
                '}';
    }
}
