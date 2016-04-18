package web.module.order;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import web.module.customer.PersonalInfo;
import web.module.menu.MenuBoundaryInterface;
import web.module.menu.MenuItem;
import web.module.menu.MenuManager;
import web.module.surcharge.Surcharge;
import web.module.utility.DateUtility;
import web.module.utility.UniqueIdGenerator;

public class Order{

	private int id;
	private String order_date;
	private String delivery_date;
	private double amount;
	private double surcharge;
	private String status;
	private String delivery_address;
	private PersonalInfo personal_info;
	private String note;
	private List<OrderDetail> order_detail = new ArrayList<>();
	
	private MenuBoundaryInterface menuManager = new MenuManager();
	
	public Order() {
	
	}

	public Order(String delivery_date, String delivery_address, PersonalInfo personal_info, String note,
			List<OrderDetail> order_detail) {
		
		this.delivery_date = delivery_date;
		this.delivery_address = delivery_address;
		this.personal_info = personal_info;
		this.note = note;
		this.order_detail = order_detail;
	}
	
	public void init() throws ParseException{
		id = UniqueIdGenerator.getUniqueOrderID();
		order_date = DateUtility.getFormattedDateToday();
		status = OrderStatus.OPEN;
		amount = calculateAmount();
		surcharge = calculateSurcharge();
	}

	public int getId() {
		return id;
	}

	public String getOrder_date() {
		return order_date;
	}

	public String getDelivery_date() {
		return delivery_date;
	}

	public double getAmount() {
		return amount;
	}

	public double getSurcharge() {
		return surcharge;
	}

	public String getStatus() {
		return status;
	}
	
	public PersonalInfo getPersonal_info() {
		return personal_info;
	}
	
	public String getDelivery_address() {
		return delivery_address;
	}

	public List<OrderDetail> getOrder_detail() {
		return order_detail;
	}

	public String getNote() {
		return note;
	}

	public boolean isNil() {
		return false;
	}

	public boolean isOrderDetailValid() {
		for(OrderDetail od : getOrder_detail()){
			int od_id = od.getId();
			int count = od.getCount();
			MenuItem item = menuManager.getItem(od_id);
			if(item.isNil() || !item.matchesMinimumOrder(count)) return false;
			else od.setName(item.getName());
		}
		return true;
	}
	
	public boolean matchesId(int id) {
		return (this.id == id);
	}

	public void cancelOrder() throws ParseException {
		status = OrderStatus.CANCELLED;
	}

	public void updateDeliveryStatus() {
		status = OrderStatus.DELIVERED;
	}

	public boolean matachesDeliveryDate(String date) throws ParseException {
		return (DateUtility.isDatesMatches(delivery_date, date));
	}
	
	public boolean matachesDeliveryDate(String start_date, String end_date) throws ParseException {
		return (DateUtility.isDateInRange(delivery_date, start_date, end_date));
	}
	
	public boolean isOrderCancellable() throws ParseException {
		String todayDate = DateUtility.getFormattedDateToday();
		return (!(DateUtility.isDatesMatches(this.delivery_date, todayDate)));
	}
	
	private double calculateAmount() {
		double total_amount = 0;
		for(OrderDetail od : getOrder_detail()){
			MenuItem item = menuManager.getItem(od.getId());
			total_amount += item.getPrice_per_person() * od.getCount();
		}
		return total_amount;
	}
	
	private double calculateSurcharge() throws ParseException {
		if(DateUtility.isDateWeekEnd(delivery_date)) return Surcharge.getSurcharge();
		return 0;
	}

}
