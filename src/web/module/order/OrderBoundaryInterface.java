package web.module.order;

import java.text.ParseException;
import java.util.List;

public interface OrderBoundaryInterface {
	
	public void addOrder(Order order) throws ParseException;
	public void cancelOrder(int id) throws ParseException;
	public Order getOrder(int id);
	
	public List<Order> getAllOrder();
	public List<Order> getAllOrder(String date) throws ParseException;

}
