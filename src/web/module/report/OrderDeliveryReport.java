package web.module.report;

import java.util.ArrayList;
import java.util.List;

import web.module.order.Order;

public class OrderDeliveryReport extends Report {

	private List<Order> orderList = new ArrayList<>();

	public OrderDeliveryReport(int id, String name) {
		super(id, name);
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

}
