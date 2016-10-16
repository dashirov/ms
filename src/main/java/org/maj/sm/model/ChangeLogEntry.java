package org.maj.sm.model;

import java.util.Date;

/**
 * Created by dashirov on 10/16/16.
 */
public class ChangeLogEntry<E> {
    private Date effectiveDate;
    private E value;

    public ChangeLogEntry() {
        super();
    }

    public ChangeLogEntry(Date effectiveDate, E value) {
        this.effectiveDate = effectiveDate;
        this.value = value;
    }

    public void setValue(E value){
        this.value = value;
    }
    public E getValue(){
        return this.value;
    }
    public void setEffectiveDate(Date effectiveDate){
        this.effectiveDate=effectiveDate;
    }

    public Date getEffectiveDate(){
        return this.effectiveDate;
    }

}
