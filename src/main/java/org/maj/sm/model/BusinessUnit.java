package org.maj.sm.model;

/**
 * Created by dashirov on 10/16/16.
 */

import com.googlecode.objectify.annotation.Subclass;
import org.maj.sm.model.enums.AccountType;

@Subclass(index=true)
public class BusinessUnit extends Account {
    private Long parentAccount;
    public BusinessUnit() {
        super();
        super.setType(AccountType.BUSINESS_UNIT);
    }
    @Override
    public void setType(AccountType ignored) {
        // do not allow to change account type
        super.setType(AccountType.BUSINESS_UNIT);
    }

    public Long getParentAccount() {
        return parentAccount;
    }
    public void setParentAccount(Long parentAccount) {
        this.parentAccount = parentAccount;
    }
}
