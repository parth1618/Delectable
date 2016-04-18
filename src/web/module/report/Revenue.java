package web.module.report;

public class Revenue {
	
	private int orders_placed;
	private int orders_open;
	private int orders_delivered;
	private int orders_cancelled;
	private double food_revenue;
	private double surcharge_revenue;
	
	public int getOrders_placed() {
		return orders_placed;
	}
	public int getOrders_open() {
		return orders_open;
	}
	public int getOrders_delivered() {
		return orders_delivered;
	}
	public int getOrders_cancelled() {
		return orders_cancelled;
	}
	public double getFood_revenue() {
		return food_revenue;
	}
	public double getSurcharge_revenue() {
		return surcharge_revenue;
	}
	public void setOrders_placed(int orders_placed) {
		this.orders_placed = orders_placed;
	}
	public void setOrders_open(int orders_open) {
		this.orders_open = orders_open;
	}
	public void setOrders_delivered(int orders_delivered) {
		this.orders_delivered = orders_delivered;
	}
	public void setOrders_cancelled(int orders_cancelled) {
		this.orders_cancelled = orders_cancelled;
	}
	public void setFood_revenue(double food_revenue) {
		this.food_revenue = food_revenue;
	}
	public void setSurcharge_revenue(double surcharge_revenue) {
		this.surcharge_revenue = surcharge_revenue;
	}
	
	
	
	

}
