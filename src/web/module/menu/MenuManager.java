package web.module.menu;

import java.util.ArrayList;
import java.util.List;

import web.module.admin.AdminMenuBoundaryInterface;

public class MenuManager implements MenuBoundaryInterface, AdminMenuBoundaryInterface {

	private static List<MenuItem> menuItemList =  new ArrayList<>();
	
	@Override
	public List<MenuItem> getAllMenuItems() {
		return menuItemList;
	}

	@Override
	public void addItem(MenuItem item) {
		if(item.isItemDetailValid()){
			item.init();
			menuItemList.add(item);
		}
		else throw new InvalidItemDetailException();
	}

	@Override
	public void updateItem(int id, MenuItem item) {
		if(item.isPriceValid()){
			MenuItem itemForUpdate = getItem(id);
			int itemIndex = getItemIndex(itemForUpdate);
			itemForUpdate.updateItem(item.getPrice_per_person());
			menuItemList.set(itemIndex, itemForUpdate);
		}
		else throw new InvalidItemPriceForUpdateException();
	}

	@Override
	public MenuItem getItem(int id) {
		for(MenuItem item : menuItemList){
			if(item.matchesId(id)) return item;
		}
		return new NullMenuItem();
	}
	
	private int getItemIndex(MenuItem item){
		return menuItemList.indexOf(item);
	}
	
	public class InvalidItemDetailException extends RuntimeException{
		private static final long serialVersionUID = 1L;
    }
	
	public class InvalidItemPriceForUpdateException extends RuntimeException{
		private static final long serialVersionUID = 1L;
	}

}
