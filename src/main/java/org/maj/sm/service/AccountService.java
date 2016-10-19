package org.maj.sm.service;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.NotFoundException;
import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;
import org.maj.sm.dao.AccountServiceDAO;
import org.maj.sm.model.Account;
import org.maj.sm.model.BusinessUnit;
import org.maj.sm.model.MSAAccount;

import com.googlecode.objectify.Key;
import org.maj.sm.model.Product;
import org.maj.sm.model.enums.ProductStatus;

import java.util.Date;

/**
 * This is a higher level service layer
 * 
 * @author shamik.majumdar
 *
 */

public class AccountService {
	
	private AccountServiceDAO accountServiceDAO;
	
	public void setAccountServiceDAO(AccountServiceDAO accountServiceDAO) {
		this.accountServiceDAO = accountServiceDAO;
	}

	public MSAAccount createMSAAccount(String name, String description) {
		 MSAAccount myWalledGarden = new MSAAccount();
		 myWalledGarden.setName(name);
	     myWalledGarden.setDescription(description);
	     return (MSAAccount) accountServiceDAO.saveAccount(myWalledGarden);
	}

	public BusinessUnit createBusinessUnit(String name, String description,Account parent){
		BusinessUnit bu = new BusinessUnit();
		bu.setName(name);
		bu.setDescription(description);
		bu.setParentAccount(parent.getId());		
		return accountServiceDAO.createBusinessUnit(bu, parent);
	}


	public BusinessUnit moveBusinessUnit(BusinessUnit businessUnit, Account newParentAccount){
        return accountServiceDAO.moveBusinessUnit(businessUnit,newParentAccount);
    }


    public Product saveProduct(Product product){
        return  accountServiceDAO.saveProduct(product);
    }

    public Product createProduct(Account parentAccount, String productCode, String productName, String productDescription) {
        Product product = new Product();
        product.setCode(productCode);
        product.setName(productName);
        product.setDescription(productDescription);
        product.setStatus(new Date(), ProductStatus.NEW);

        parentAccount.addProduct(product.getCode());
        product.setParentAccount(parentAccount.getId());
        accountServiceDAO.saveAccount(parentAccount);
        return  accountServiceDAO.saveProduct(product);
    }

    public Product moveProduct(Product product, Account newParentAccount){
        return accountServiceDAO.moveProduct(product,newParentAccount);
    }

}
