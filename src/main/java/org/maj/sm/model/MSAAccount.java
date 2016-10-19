package org.maj.sm.model;

/**
 * Created by dashirov on 10/16/16.
 */

import com.googlecode.objectify.annotation.Container;
import com.googlecode.objectify.annotation.Subclass;
import org.maj.sm.model.enums.AccountType;

import java.util.SortedSet;
import java.util.TreeSet;

@Subclass(index=true)
public class MSAAccount extends Account {

    @Container
    private SortedSet<Long> marketplaces = new TreeSet<>();

    public MSAAccount() {
        super();
        super.setType(AccountType.MSA);
    }
    @Override
    public void setType(AccountType ignored){
        // Do not allow to chaange account type
        super.setType(AccountType.MSA);
    }

    public void addMarketplace(Marketplace marketplace){
        marketplaces.add(marketplace.getId());
    }

    public void removeMarketplace(Marketplace marketplace){
        marketplaces.remove(marketplace.getId());
    }

    public SortedSet<Long> getMarketplaces() {
        return marketplaces;
    }

    public void setMarketplaces(SortedSet<Long> marketplaces) {
        this.marketplaces = marketplaces;
    }
}
