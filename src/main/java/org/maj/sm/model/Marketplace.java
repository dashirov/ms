package org.maj.sm.model;

/**
 * Created by dashirov on 10/16/16.
 *
 * AppNexus, Google, Yahoo! Gemini, Facebook, Instagram are marketplaces where you place a purchase order,
 *  insertion order and spend money on user acquisition
 */
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.maj.sm.model.enums.MarketplaceStatus;


@Entity
public class Marketplace {
    @Id private Long id;
    private String name;
    private Long parentAccount;
    private MarketplaceStatus status;

    public Marketplace() {
        status=MarketplaceStatus.NEW;
    }

    public Long getId() {
        return id;
    }

    public MarketplaceStatus getStatus() {
        return status;
    }

    public void setStatus(MarketplaceStatus status) {
        this.status = status;
    }

    public Long getParentAccount() {
        return parentAccount;
    }

    public void setParentAccount(Long parentAccount) {
        this.parentAccount = parentAccount;
    }

    public void setParentAccount(MSAAccount account){
        this.parentAccount = account.getId();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Marketplace{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
