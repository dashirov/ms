package org.maj.sm.model;

/**
 * Created by dashirov on 10/16/16.
 */
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.maj.sm.model.enums.AccountStatus;
import org.maj.sm.model.enums.AccountType;
import org.maj.sm.utility.ChangeLogEntryComparator;

import java.util.SortedSet;
import java.util.TreeSet;

@Entity
public class Account {
    @Id public Long id;
    public AccountType type;  // TODO: Make sure enum gets into ndb as a string value
    public String name;
    public String description;
    SortedSet<ChangeLogEntry<AccountStatus>> statusChangeLog = new TreeSet<>(new ChangeLogEntryComparator());
}
