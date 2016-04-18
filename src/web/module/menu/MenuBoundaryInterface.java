package web.module.menu;

import java.util.List;

public interface MenuBoundaryInterface {
	
	public List<MenuItem> getAllMenuItems();
	public MenuItem getItem(int id);
}
