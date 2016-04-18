package web.module.report;

import java.text.ParseException;

import web.module.order.OrderManager;

public class Report803 implements ReportGeneratorInterface{

	private ReportRevenueBoundaryInterface orderManager = new OrderManager();
	
	@Override
	public Report generateReport(Report report, String start_date, String end_date) throws ParseException {
		
		RevenueReport revenueReport = (RevenueReport)report;
		Revenue revenue = null;
		
		if(start_date != null || end_date != null) {
			revenue = orderManager.calculateRevenue(start_date, end_date);
			
			if(start_date == null)start_date = "Not Applicable";
			if(end_date == null)end_date = "Not Applicable";
			
			revenueReport.setStart_date(start_date);
			revenueReport.setEnd_date(end_date);
		}
		else {
			revenue = orderManager.calculateRevenue();
			
			revenueReport.setStart_date("Not Applicable");
			revenueReport.setEnd_date("Not Applicable");
		}
		revenueReport.setRevenue(revenue);
		
		return revenueReport;
	}

}
