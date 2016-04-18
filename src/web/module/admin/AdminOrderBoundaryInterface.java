package web.module.admin;

import web.module.order.Order;

public interface AdminOrderBoundaryInterface {
	
	public Order getOrder(int oid);
	public void updateDeliveryStatus(int oid);
}
