package web.module.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import web.module.order.Order;
import web.module.utility.UniqueIdGenerator;

public class Customer {
	
	private int id;
	private PersonalInfo personal_info;
	private List<Alias> known_alias = new ArrayList<>();
	private List<Order> order_history = new ArrayList<>();
	
	public Customer() {
		id = UniqueIdGenerator.getUniqueCustomerID();
	}

	public int getId() {
		return id;
	}
	
	public void setPersonal_info(PersonalInfo personal_info) {
		this.personal_info = personal_info;
	}

	public PersonalInfo getPersonal_info() {
		return personal_info;
	}

	public List<Alias> getKnown_alias() {
		return known_alias;
	}

	public List<Order> getOrder_history() {
		return order_history;
	}
	
	public void addOrder(Order order){
		order_history.add(order);
	}
	
	public void addAlias(Alias alias){
		known_alias.add(alias);
	}
	
	public boolean isNil(){
		return false;
	}

	public boolean matches(String query_string) {
		Pattern pattern = Pattern.compile(query_string, Pattern.CASE_INSENSITIVE);
		return pattern.matcher(getObjectString()).find();
	}
	
	public boolean isAliasMatches(){
		Alias al = new Alias(personal_info.getName(), personal_info.getPhone());
		return known_alias.contains(al);
	}
	
	public void removeAnyDuplicateAlias(PersonalInfo person_Info){
		List<Alias> aliasToRemove = new ArrayList<>();
		for(Alias a : known_alias){
			if(a.getName().equals(person_Info.getName()) && a.getPhone().equals(person_Info.getPhone()))aliasToRemove.add(a);
		}
		known_alias.removeAll(aliasToRemove);
	}
	
	private String getObjectString(){
		String matchString = personal_info.getEmail() + " " + personal_info.getName() + " " + personal_info.getPhone();
		
		for(Alias al : getKnown_alias()){
			matchString += " " + al.getName();
			matchString += " " + al.getPhone();
		}
		return matchString;
	}
	
	

}
