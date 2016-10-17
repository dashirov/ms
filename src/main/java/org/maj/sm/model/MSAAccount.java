package org.maj.sm.model;

/**
 * Created by dashirov on 10/16/16.
 */
import com.googlecode.objectify.annotation.Subclass;
import org.maj.sm.model.enums.AccountOwnershipStatus;
import org.maj.sm.model.enums.AccountType;
import org.maj.sm.utility.ChangeLogEntryComparator;

import java.util.*;

@Subclass(index=true)
public class MSAAccount extends Account {
    public MSAAccount() {
        super();
        this.setType(AccountType.MSA);
    }

}
