package web.module.menu;

import java.util.ArrayList;
import java.util.List;

import web.module.utility.DateUtility;
import web.module.utility.UniqueIdGenerator;

public class MenuItem{

	private int id;
	private String name;
	private double price_per_person;
	private int minimum_order;
	private List<Category> categories = new ArrayList<>();
	private String created_date;
	private String last_modified_date;
	
	public MenuItem() {
		
	}

	public MenuItem(String name, double price_per_person, int minimum_order, List<Category> categories) {
		super();
		this.name = name;
		this.price_per_person = price_per_person;
		this.minimum_order = minimum_order;
		this.categories = categories;
	}
	
	public void init(){
		id = UniqueIdGenerator.getUniqueItemID();
		created_date = DateUtility.getFormattedDateToday();
		last_modified_date = created_date;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice_per_person() {
		return price_per_person;
	}

	public int getMinimum_order() {
		return minimum_order;
	}

	public List<Category> getCategories() {
		return categories;
	}
	
	public String getCreated_date() {
		return created_date;
	}

	public String getLast_modified_date() {
		return last_modified_date;
	}

	public boolean isNil() {
		return false;
	}

	public boolean isItemDetailValid() {
		return (price_per_person > 0 && minimum_order > 0);
	}

	public boolean matchesId(int id) {
		return (this.id == id);
	}

	public boolean isPriceValid() {
		return (price_per_person > 0);
	}

	public boolean matchesMinimumOrder(int count) {
		return (count >= minimum_order);
	}

	public void updateItem(double price_per_person) {
		this.price_per_person = price_per_person;
		last_modified_date = DateUtility.getFormattedDateToday();
	}

	
}
