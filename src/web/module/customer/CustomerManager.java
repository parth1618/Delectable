package web.module.customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import web.module.order.Order;

public class CustomerManager implements CustomerBoundaryInterface, CustomerAliasInterface {

	private static Map<String, Customer> customerList = new HashMap<>();

	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> listCustomer = new ArrayList<>();
		for (Map.Entry<String, Customer> entry : customerList.entrySet()) {
			Customer cstmr = entry.getValue();
			listCustomer.add(cstmr);
		}
		Collections.sort(listCustomer, new Comparator<Customer>() {
			@Override
			public int compare(Customer c1, Customer c2) {
				return c1.getId() - c2.getId();

			}
		});
		return listCustomer;
	}

	@Override
	public List<PersonalInfo> getAllCustomer(String query_string) {
		List<PersonalInfo> listCustomer = new ArrayList<>();
		for (Map.Entry<String, Customer> entry : customerList.entrySet()) {
			Customer cstmr = entry.getValue();
			if (cstmr.matches(query_string))
				listCustomer.add(cstmr.getPersonal_info());
		}
		return listCustomer;
	}

	@Override
	public Customer getCustomer(int id) {
		for (Map.Entry<String, Customer> entry : customerList.entrySet()) {
			Customer cstmr = entry.getValue();
			if (cstmr.getId() == id)
				return cstmr;
		}
		return new NullCustomer();
	}

	@Override
	public void saveOrUpdateCustomer(PersonalInfo personal_info) {
		Customer cstmr;
		if (contains(personal_info.getEmail())) {

			cstmr = getCustomer(personal_info.getEmail());

			if (!cstmr.isAliasMatches() && !cstmr.getPersonal_info().equals(personal_info)) {

				cstmr.addAlias(new Alias(cstmr.getPersonal_info().getName(), cstmr.getPersonal_info().getPhone()));
				cstmr.removeAnyDuplicateAlias(personal_info);
			}
		} else {
			cstmr = new Customer();
		}
		cstmr.setPersonal_info(personal_info);
		customerList.put(personal_info.getEmail(), cstmr);
	}

	@Override
	public void saveOrderHistory(Order order) {
		Customer cstmr = getCustomer(order.getPersonal_info().getEmail());
		cstmr.addOrder(order);
	}

	private boolean contains(String email) {
		return customerList.containsKey(email);
	}

	private Customer getCustomer(String email) {
		return customerList.get(email);
	}

}
