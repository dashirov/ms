package org.maj.sm.model;

/**
 * Created by dashirov on 10/16/16.
 */
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Subclass;
import org.maj.sm.model.enums.AccountStatus;
import org.maj.sm.model.enums.AccountType;
import org.maj.sm.utility.ChangeLogEntryComparator;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

@Subclass(index=true)
public class BusinessUnit extends Account {
    private Long parentAccount;
    private Map<Long,Campaign> campaigns;
    private Map<Long,Product> products;

    public BusinessUnit() {
        super();
        this.type=AccountType.BUSINESS_UNIT;
    }
}
