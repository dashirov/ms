package org.maj.sm.model;

/**
 * Created by dashirov on 10/16/16.
 */
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.maj.sm.model.enums.AccountOwnershipStatus;
import org.maj.sm.model.enums.AccountStatus;
import org.maj.sm.model.enums.AccountType;
import org.maj.sm.utility.AccountComparator;
import org.maj.sm.utility.ChangeLogEntryComparator;

import java.util.*;

@Entity
public class Account {
    @Id public Long id;
    public AccountType type;  // TODO: Make sure enum gets into ndb as a string value
    public String name;
    public String description;
    SortedSet<ChangeLogEntry<AccountStatus>> statusChangeLog = new TreeSet<>(new ChangeLogEntryComparator());

    /**
     * A sorted map keyed by a subaccount (that was ever subjugated to this account )
     *       value is the status change history log of subjugation
     *       SortedSet<ChangeLogEntry<Double>> priceChangeLog = new TreeSet<>(new ChangeLogEntryComparator());
     *
     *       why sorted? I want api to return data in the same order from different nodes, jvm implementations
     */
    SortedMap accountOwnershipLog
            = Collections.synchronizedSortedMap(
            new TreeMap<BusinessUnit,SortedSet<ChangeLogEntry<AccountOwnershipStatus>>>(new AccountComparator())
    );

    public void subjugateAccount(BusinessUnit businessUnit){
        // unlink account from its parent, link to this as a parent, update this.accountOwnershipLog
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
