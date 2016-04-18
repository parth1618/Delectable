package web.module.customer;

public interface CustomerAliasInterface {
	
	public void saveOrUpdateCustomer(PersonalInfo personal_info);
	public void saveOrderHistory(web.module.order.Order order);

}
