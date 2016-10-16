package org.maj.sm.model;

/**
 * Created by dashirov on 10/16/16.
 *
 * AppNexus, Google, Yahoo! Gemini, Facebook, Instagram are marketplaces where you place a purchase order,
 *  insertion order and spend money on user acquisition
 */
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Marketplace {
    @Id public Long id;
    public String name;
}
