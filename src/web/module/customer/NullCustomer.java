package web.module.customer;

public class NullCustomer extends Customer{
	@Override
	public boolean isNil(){
		return true;
	}
}
