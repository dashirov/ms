package org.maj.sm.model;

/**
 * Created by dashirov on 10/16/16.
 */

import com.googlecode.objectify.annotation.Subclass;
import org.maj.sm.model.enums.AccountType;

@Subclass(index=true)
public class MSAAccount extends Account {
    public MSAAccount() {
        super();
        super.setType(AccountType.MSA);
    }
    @Override
    public void setType(AccountType ignored){
        // Do not allow to chaange account type
        super.setType(AccountType.MSA);
    }
}
