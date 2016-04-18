package web.module.order;

public class OrderDetail {
	
	private int id;
	private String name;
	private int count;
	
	public OrderDetail(){
		
	}
	
	public OrderDetail(int id, int count) {
		super();
		this.id = id;
		this.count = count;
	}

	public int getId() {
		return id;
	}
	public int getCount() {
		return count;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
