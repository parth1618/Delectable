package web.module.admin;

import web.module.menu.MenuItem;
import web.module.menu.MenuManager;
import web.module.menu.MenuManager.InvalidItemDetailException;
import web.module.menu.MenuManager.InvalidItemPriceForUpdateException;
import web.module.order.Order;
import web.module.order.OrderManager;
import web.module.surcharge.Surcharge;

public class AdminManager implements AdminBoundaryInterface {
	
	private AdminMenuBoundaryInterface menuManager = new MenuManager();
	private AdminOrderBoundaryInterface orderManager = new OrderManager();

	@Override
	public void addItem(MenuItem item) throws InvalidItemDetailException{
		menuManager.addItem(item);
	}

	@Override
	public void updateItem(int id, MenuItem item) throws InvalidItemPriceForUpdateException{
		menuManager.updateItem(id, item);
	}

	@Override
	public MenuItem getItem(int id) {
		return menuManager.getItem(id);
	}

	@Override
	public void addSurcharge(double charge) {
		Surcharge.setSurcharge(charge);
	}

	@Override
	public double getSurcharge() {
		return Surcharge.getSurcharge();
	}

	@Override
	public Order getOrder(int oid) {
		return orderManager.getOrder(oid);
	}

	@Override
	public void updateDeliveryStatus(int oid) {
		orderManager.updateDeliveryStatus(oid);
	}

	@Override
	public boolean isSurchargeSet() {
		return Surcharge.isSet();
	}

}
