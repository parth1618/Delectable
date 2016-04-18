package web.module.customer;

import java.util.List;

public interface CustomerBoundaryInterface {
	
	public List<Customer> getAllCustomer();
	public List<PersonalInfo> getAllCustomer(String query_string);
	public Customer getCustomer(int id);

}
