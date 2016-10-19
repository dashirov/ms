package org.maj.sm.dao;

import org.maj.sm.model.Account;
import org.maj.sm.model.BusinessUnit;
import org.maj.sm.model.MSAAccount;
import org.maj.sm.model.Product;

public interface AccountServiceDAO {
    /**
     * Save an account.
     * Saves MSAAccount and BusinessUnit type accounts
     * If this is a new account (id is not defined) then account will be created
     * otherwise existing object will be updated
     * @param account
     * @return
     */
    Account saveAccount(Account account);

    /**
     * Convenience method - create a new business unit, under a particular parent account
     * Parent can be either MSAAccount or BusinessUnit account
     * @param businessUnit
     * @param parent
     * @return
     */
    BusinessUnit createBusinessUnit(BusinessUnit businessUnit, Account parent);

    /**
     * Move a Business Unit account by detaching it from it's parent and attaching under a new parent
     * @param bu
     * Business Unit to move
     * @param parent
     * New Parent to attach to
     * @return
     * Updated Business Unit
     */
	BusinessUnit moveBusinessUnit(BusinessUnit bu, Account parent);

    /**
     * Save a product under an account
     *
     * @param product
     * Product code must be pre-populated in the POJO
     * @return the saved product
     */
    Product saveProduct(Product product);

    /**
     * Create a new product under MSA Account or Business Unit
     * @param product product POJO
     * @param parentAccount product parent account
     * @return the saved product
     */
    Product createProduct(Product product, Account parentAccount);

    /**
     * Move a product from one parent account to another
     * @param product
     * Product to move
     * @param parent
     * New Parent to attach to
     * @return
     * Updated product
     */
    Product moveProduct(Product product, Account parent);

}
