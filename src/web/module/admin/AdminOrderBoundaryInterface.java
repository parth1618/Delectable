package web.module.admin;

import java.text.ParseException;

import web.module.order.Order;

public interface AdminOrderBoundaryInterface {
	
	public Order getOrder(int oid);
	public void updateDeliveryStatus(int oid) throws ParseException;
}
