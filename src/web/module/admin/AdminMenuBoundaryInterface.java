package web.module.admin;

import web.module.menu.MenuItem;

public interface AdminMenuBoundaryInterface {
	
	public void addItem(MenuItem item);
	public void updateItem(int id, MenuItem item);
	public MenuItem getItem(int id);

}
