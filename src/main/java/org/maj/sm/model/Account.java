package org.maj.sm.model;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Account {

	@Id private Long id;
	private String name;	
	private SortedSet<ChangeLog<Long>> parentAccounts = new TreeSet<>();
	private SortedSet<ChangeLog<Long>> childAccounts = new TreeSet<>();
	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public long getParentAccount(){
		return parentAccounts.last().value;
	}
	
	public long getChildAccount(){
		return childAccounts.last().value;
	}
	
	public void addParentAccount(Date date, Long pAccount){
		this.parentAccounts.add(new ChangeLog<Long>(pAccount,date));
	}
	
	public void addChildAccount(Date date, Long cAccount){
		this.childAccounts.add(new ChangeLog<Long>(cAccount,date));
	}
	
}
