package web.module.report;

public class RevenueReport extends Report {

	private Revenue revenue;

	public RevenueReport(int id, String name) {
		super(id, name);
	}

	public void setRevenue(Revenue revenue) {
		this.revenue = revenue;
	}

	public Revenue getRevenue() {
		return revenue;
	}

}
