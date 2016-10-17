package org.maj.sm.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

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

    SortedSet<ChangeLogEntry<Double>> priceChangeLog = new TreeSet<>(new ChangeLogEntryComparator());
    SortedSet<ChangeLogEntry<CampaignStatus>> statusChangeLog = new TreeSet<>(new ChangeLogEntryComparator());
	
	public Campaign() {
		super();
	}

	public Campaign(String code, String description) {
		super();
		this.code = code;
		this.description = description;
	}
	
	public void setPrice(Date effectiveDate, Double campaignPrice){
		this.priceChangeLog.add(new ChangeLogEntry(effectiveDate,campaignPrice)
		);
	}

	public void setStatus(Date effectiveDate, CampaignStatus campaignStatus){
        this.statusChangeLog.add(new ChangeLogEntry<CampaignStatus>(effectiveDate,campaignStatus));
    }

    public CampaignStatus getStatus(){
        return getStatus(new Date());
    }

    public CampaignStatus getStatus(Date date) {
        for (ChangeLogEntry<CampaignStatus> changeLogEntry: statusChangeLog
             ) {
            if (changeLogEntry.getEffectiveDate().before(date) || changeLogEntry.getEffectiveDate().equals(date)){
                return changeLogEntry.getValue();
            }

        }
        return CampaignStatus.DELETED;
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
         * Now that we know it was active, find the price
         */
        for (ChangeLogEntry<Double> changeLogEntry: priceChangeLog
             ) {
            if (changeLogEntry.getEffectiveDate().before(date) || changeLogEntry.getEffectiveDate().equals(date)){
                return changeLogEntry.getValue();
            }
        }
        return 0.0;
    }

    public String statusChangeLogAsString(){
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        sb.append("PriceChangeLog[");
        for (ChangeLogEntry<CampaignStatus> e: statusChangeLog
                ) {
            sb.append("{");
            sb.append(df.format(e.getEffectiveDate()));
            sb.append(": ");
            sb.append(e.getValue().toString());
            sb.append("}");
        }
        sb.append("]");
        return sb.toString();
    }

    public String priceChangeLogAsString(){
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        sb.append("PriceChangeLog[");
        for (ChangeLogEntry<Double> e: priceChangeLog
             ) {
            sb.append(" {");
            sb.append(df.format(e.getEffectiveDate()));
            sb.append(": ");
            sb.append(e.getValue().toString());
            sb.append("} ");
        }
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return "Campaign{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", marketplace=" + marketplace +
                ", product='" + product + '\'' +

                ", price=" + this.getPrice(new Date()).toString() +
                ", priceChangeLog=" + this.priceChangeLogAsString()+

                ", status=" + this.getStatus(new Date()).toString() +
                ", statusChangeLog=" + this.statusChangeLogAsString() +
                '}';
    }
}
