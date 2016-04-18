package web.module.admin;

import java.text.ParseException;

import web.module.menu.MenuItem;
import web.module.order.Order;

public interface AdminBoundaryInterface {

	public void addItem(MenuItem item);
	public void updateItem(int id, MenuItem item);
	public MenuItem getItem(int id);

	public Order getOrder(int oid);
	public void updateDeliveryStatus(int oid) throws ParseException;

	public void addSurcharge(double charge);
	public double getSurcharge();
	public boolean isSurchargeSet();

}
