package web.module.order;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import web.module.admin.AdminOrderBoundaryInterface;
import web.module.customer.CustomerAliasInterface;
import web.module.customer.CustomerManager;
import web.module.report.ReportOrderBoundaryInterface;
import web.module.report.ReportRevenueBoundaryInterface;
import web.module.report.Revenue;

public class OrderManager implements OrderBoundaryInterface, AdminOrderBoundaryInterface, ReportOrderBoundaryInterface, ReportRevenueBoundaryInterface {
	
	private static List<Order> orderList = new ArrayList<>();
	
	private CustomerAliasInterface customerManager = new CustomerManager();

	@Override
	public void addOrder(Order order) throws ParseException {
		if(order.isOrderDetailValid()){
			order.init();
			orderList.add(order);
			customerManager.saveOrUpdateCustomer(order.getPersonal_info());
			customerManager.saveOrderHistory(order);
		}
		else throw new InvalidOrderDetailException();
	}

	@Override
	public void cancelOrder(int id) throws ParseException {
		Order o = getOrder(id);
		if(o.isOrderCancellable()) {
			o.cancelOrder();
			orderList.set(orderList.indexOf(o), o);
		}
		else throw new OrderCanNotBeCancelledException();
	}

	@Override
	public Order getOrder(int id) {
		for(Order order : orderList){
			if(order.matchesId(id)) return order;
		}
		return new NullOrder();
	}

	@Override
	public List<Order> getAllOrder() {
		return orderList;
	}

	@Override
	public List<Order> getAllOrder(String date) throws ParseException {
		List<Order> filteredList = new ArrayList<>();
		Iterator<Order> iterator = orderList.listIterator();
		while(iterator.hasNext()){
			Order o = iterator.next();
			if(o.matachesDeliveryDate(date) && o.getStatus().equals(OrderStatus.OPEN)) filteredList.add(o);
		}
		return filteredList;
	}

	@Override
	public List<Order> getAllOrder(String startDate, String endDate) throws ParseException {
		List<Order> filteredList = new ArrayList<>();
		Iterator<Order> iterator = orderList.listIterator();
		while(iterator.hasNext()){
			Order o = iterator.next();
			if(o.matachesDeliveryDate(startDate, endDate)) filteredList.add(o);
		}
		return filteredList;
	}
	
	@Override
	public void updateDeliveryStatus(int oid) {
		Order o = getOrder(oid);
		o.updateDeliveryStatus();
		orderList.set(orderList.indexOf(o), o);
	}
	
	public class InvalidOrderDetailException extends RuntimeException{
		private static final long serialVersionUID = 1L;
    }

	@Override
	public Revenue calculateRevenue(String startDate, String endDate) throws ParseException {
		List<Order> orderList = getAllOrder(startDate, endDate);
		return doReveueCalculation(orderList);
	}
	
	@Override 
	public Revenue calculateRevenue(){
		List<Order> orderList = getAllOrder();
		return doReveueCalculation(orderList);
	}

	private Revenue doReveueCalculation(List<Order> orderList) {
		int total_order = 0;
		int total_open_order = 0;
		int total_cancelled_order = 0;
		int total_delivered_order = 0;
		double total_food_revenue = 0;
		double total_surcharge_revenue = 0;
		
		for(Order order : orderList){
			if(order.getStatus().equals(OrderStatus.DELIVERED)){
				total_delivered_order++;
				total_food_revenue += order.getAmount();
			}
			else if(order.getStatus().equals(OrderStatus.CANCELLED)){
				total_cancelled_order++;
				total_food_revenue -= order.getAmount();
			}
			else {
				
				total_open_order++;
				total_food_revenue += order.getAmount();
			}
			total_surcharge_revenue += order.getSurcharge();
			total_order++;
		}
		
		Revenue revenue = new Revenue();
		
		revenue.setOrders_placed(total_order);
		revenue.setOrders_open(total_open_order);
		revenue.setOrders_delivered(total_delivered_order);
		revenue.setOrders_cancelled(total_cancelled_order);
		revenue.setFood_revenue(total_food_revenue);
		revenue.setSurcharge_revenue(total_surcharge_revenue);
		
		return revenue;
		
	}
	
	public class OrderCanNotBeCancelledException extends RuntimeException{
		private static final long serialVersionUID = 1L;
    }


	
}
